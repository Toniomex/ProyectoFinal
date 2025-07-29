/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyectofinal.controller;

/**
 *
 * @author antoine
 */

    import com.proyectofinal.model.Contrato;
    import com.proyectofinal.service.ContratoService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

    import java.util.List;
    import java.util.Optional;

    /**
     * Controlador REST para la gestión de Contratos.
     * Proporciona endpoints para crear, obtener, actualizar y eliminar contratos.
     */
    @RestController
    @RequestMapping("/api/contratos") // Prefijo para todas las rutas de este controlador
    public class ContratoController {

        @Autowired
        private ContratoService contratoService;

        /**
         * Crea un nuevo contrato.
         * @param contrato El objeto Contrato a crear.
         * @return ResponseEntity con el contrato creado y estado HTTP 201.
         */
        @PostMapping
        public ResponseEntity<Contrato> crearContrato(@RequestBody Contrato contrato) {
            Contrato nuevoContrato = contratoService.crearContrato(contrato); // Aquí se llama con un solo argumento
            return new ResponseEntity<>(nuevoContrato, HttpStatus.CREATED);
        }

        /**
         * Obtiene un contrato por su ID.
         * @param id El ID del contrato.
         * @return ResponseEntity con el contrato y estado HTTP 200, o 404 si no se encuentra.
         */
        @GetMapping("/{id}")
        public ResponseEntity<Contrato> obtenerContratoPorId(@PathVariable Long id) {
            Optional<Contrato> contrato = contratoService.obtenerContratoPorId(id);
            return contrato.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                           .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }

        /**
         * Obtiene todos los contratos asociados a un inquilino.
         * @param idInquilino El ID de la persona inquilina.
         * @return ResponseEntity con la lista de contratos y estado HTTP 200, o 404 si no se encuentran.
         */
        @GetMapping("/inquilino/{idInquilino}")
        public ResponseEntity<List<Contrato>> obtenerContratosPorInquilino(@PathVariable Long idInquilino) {
            List<Contrato> contratos = contratoService.obtenerContratosPorInquilino(idInquilino); // Aquí se llama correctamente
            if (contratos.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(contratos, HttpStatus.OK);
        }

        /**
         * Actualiza un contrato existente.
         * @param id El ID del contrato a actualizar.
         * @param contrato El objeto Contrato con los datos actualizados.
         * @return ResponseEntity con el contrato actualizado y estado HTTP 200, o 404 si no se encuentra.
         */
        @PutMapping("/{id}")
        public ResponseEntity<Contrato> actualizarContrato(@PathVariable Long id, @RequestBody Contrato contrato) {
            // Asegurarse de que el ID del contrato en el cuerpo coincida con el ID de la ruta
            if (!id.equals(contrato.getIdContrato())) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            Optional<Contrato> existingContrato = contratoService.obtenerContratoPorId(id);
            if (existingContrato.isPresent()) {
                Contrato contratoActualizado = contratoService.actualizarContrato(contrato);
                return new ResponseEntity<>(contratoActualizado, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }

        /**
         * Elimina un contrato por su ID.
         * @param id El ID del contrato a eliminar.
         * @return ResponseEntity con estado HTTP 204 si se elimina correctamente, o 404 si no se encuentra.
         */
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> eliminarContrato(@PathVariable Long id) {
            Optional<Contrato> contrato = contratoService.obtenerContratoPorId(id);
            if (contrato.isPresent()) {
                contratoService.eliminarContrato(id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
    }