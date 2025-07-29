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
import com.proyectofinal.repository.PersonaRepository;
import com.proyectofinal.service.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; // Importar BCryptPasswordEncoder
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PersonaServiceImpl implements PersonaService {

    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder; // Inyectar el codificador de contraseñas

    @Override
    @Transactional
    public Persona registrarPersona(Persona persona) {
        // Antes de guardar, encriptar la contraseña
        String encodedPassword = bCryptPasswordEncoder.encode(persona.getContraseña());
        persona.setContraseña(encodedPassword);
        return personaRepository.save(persona);
    }

    @Override
    public Optional<Persona> autenticarPersona(String mail, String password) {
        // Este método ya no es el principal para la autenticación en el controlador,
        // pero se mantiene si es usado en otras partes o para pruebas directas.
        Optional<Persona> personaOpt = personaRepository.findByMail(mail);
        if (personaOpt.isPresent()) {
            Persona persona = personaOpt.get();
            // Comparar la contraseña proporcionada con la contraseña encriptada
            if (bCryptPasswordEncoder.matches(password, persona.getContraseña())) {
                return Optional.of(persona);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Persona> obtenerPersonaPorId(Long id) {
        return personaRepository.findById(id);
    }

    @Override
    public Optional<Persona> obtenerPersonaPorMail(String mail) { // IMPLEMENTACIÓN DEL NUEVO MÉTODO
        return personaRepository.findByMail(mail);
    }
}