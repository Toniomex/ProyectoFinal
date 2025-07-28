/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyectofinal.service;

/**
 *
 * @author antoine
 */

import com.proyectofinal.model.Persona;
import java.util.Optional; // Necesario para Optional

public interface PersonaService {
    Persona registrarPersona(Persona persona);
    Optional<Persona> autenticarPersona(String mail, String password);
    Optional<Persona> obtenerPersonaPorId(Long id);
    // Puedes añadir más métodos CRUD si los necesitas (ej. actualizar, eliminar)
}