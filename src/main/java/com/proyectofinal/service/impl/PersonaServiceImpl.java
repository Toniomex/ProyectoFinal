/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyectofinal.service.impl;

/**
 *
 * @author antoine
 */
import com.proyectofinal.model.Persona;
import com.proyectofinal.repository.PersonaRepository; // Necesitarás crear esta interfaz de repositorio
import com.proyectofinal.service.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service // Anotación para que Spring la detecte como un componente de servicio
public class PersonaServiceImpl implements PersonaService {

    @Autowired
    private PersonaRepository personaRepository; // Inyección del repositorio

    @Override
    public Persona registrarPersona(Persona persona) {
        // Aquí podrías añadir lógica de negocio, como hashear la contraseña
        // Por ahora, solo guarda la persona directamente
        return personaRepository.save(persona);
    }

    @Override
    public Optional<Persona> autenticarPersona(String mail, String password) {
        // En una aplicación real, aquí compararías la contraseña hasheada
        Optional<Persona> persona = personaRepository.findByMail(mail); // Necesitarás este método en PersonaRepository
        if (persona.isPresent() && persona.get().getContraseña().equals(password)) {
            return persona;
        }
        return Optional.empty();
    }

    @Override
    public Optional<Persona> obtenerPersonaPorId(Long id) {
        return personaRepository.findById(id);
    }
}