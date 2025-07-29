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
import java.util.List;
import java.util.Optional;

public interface PersonaService {
    Persona registrarPersona(Persona persona);
    Optional<Persona> obtenerPersonaPorId(Long id);
    Optional<Persona> obtenerPersonaPorMail(String mail);
    List<Persona> obtenerTodasLasPersonas();
    Persona actualizarPersona(Persona persona);
    void eliminarPersona(Long id);
    void autenticarPersona(String mail, String contraseña); // Método para autenticar
}