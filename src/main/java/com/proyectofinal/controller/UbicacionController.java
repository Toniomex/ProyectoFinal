/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyectofinal.controller;

/**
 *
 * @author antoine
 */

import com.proyectofinal.model.Ubicacion;
import com.proyectofinal.service.UbicacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Controlador REST para la gestión de Ubicaciones.
 * Proporciona endpoints para crear, obtener y actualizar ubicaciones.
 */
@RestController
@RequestMapping("/api/ubicaciones") // Prefijo para todas las rutas de este controlador
public class UbicacionController {

    @Autowired
    private UbicacionService ubicacionService;

    /**
     * Crea una nueva ubicación.
     * @param ubicacion El objeto Ubicacion a crear.
     * @return ResponseEntity con la ubicación creada y estado HTTP 201.
     */
    @PostMapping
    public ResponseEntity<Ubicacion> crearUbicacion(@RequestBody Ubicacion ubicacion) {
        Ubicacion nuevaUbicacion = ubicacionService.guardarUbicacion(ubicacion);
        return new ResponseEntity<>(nuevaUbicacion, HttpStatus.CREATED);
    }

    /**
     * Obtiene una ubicación por su ID.
     * @param id El ID de la ubicación.
     * @return ResponseEntity con la ubicación y estado HTTP 200, o 404 si no se encuentra.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Ubicacion> obtenerUbicacionPorId(@PathVariable Long id) {
        Optional<Ubicacion> ubicacion = ubicacionService.obtenerUbicacionPorId(id);
        return ubicacion.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                        .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Actualiza una ubicación existente.
     * @param id El ID de la ubicación a actualizar.
     * @param ubicacion El objeto Ubicacion con los datos actualizados.
     * @return ResponseEntity con la ubicación actualizada y estado HTTP 200, o 404 si no se encuentra.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Ubicacion> actualizarUbicacion(@PathVariable Long id, @RequestBody Ubicacion ubicacion) {
        // Asegurarse de que el ID de la ubicación en el cuerpo coincida con el ID de la ruta
        if (!id.equals(ubicacion.getIdUbicacion())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<Ubicacion> existingUbicacion = ubicacionService.obtenerUbicacionPorId(id);
        if (existingUbicacion.isPresent()) {
            Ubicacion ubicacionActualizada = ubicacionService.guardarUbicacion(ubicacion); // guardarUbicacion también sirve para actualizar
            return new ResponseEntity<>(ubicacionActualizada, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Elimina una ubicación por su ID.
     * @param id El ID de la ubicación a eliminar.
     * @return ResponseEntity con estado HTTP 204 si se elimina correctamente, o 404 si no se encuentra.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUbicacion(@PathVariable Long id) {
        Optional<Ubicacion> ubicacion = ubicacionService.obtenerUbicacionPorId(id);
        if (ubicacion.isPresent()) {
            // Nota: La eliminación de una ubicación puede requerir lógica adicional
            // si está asociada a contratos existentes (ej. desasociar o eliminar en cascada).
            // Para esta demo, asumimos que no hay restricciones que impidan la eliminación directa.
            ubicacionService.eliminarUbicacion(id); // Asumiendo que existe este método en el servicio
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
