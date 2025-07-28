/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyectofinal.service;

/**
 *
 * @author antoine
 */

import com.proyectofinal.model.Ubicacion;
import java.util.Optional;

public interface UbicacionService {
    Ubicacion guardarUbicacion(Ubicacion ubicacion);
    Optional<Ubicacion> obtenerUbicacionPorId(Long id);
    // Otros métodos CRUD si son necesarios
}
