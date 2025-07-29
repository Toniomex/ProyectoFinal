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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PersonaServiceImpl implements PersonaService {

    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder; // Inyectar el encoder

    @Autowired
    private AuthenticationManager authenticationManager; // Inyectar AuthenticationManager

    @Override
    @Transactional
    public Persona registrarPersona(Persona persona) {
        // Encriptar la contraseña antes de guardar
        persona.setContraseña(bCryptPasswordEncoder.encode(persona.getContraseña()));
        return personaRepository.save(persona);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Persona> obtenerPersonaPorId(Long id) {
        return personaRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Persona> obtenerPersonaPorMail(String mail) {
        return personaRepository.findByMail(mail);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Persona> obtenerTodasLasPersonas() {
        return personaRepository.findAll();
    }

    @Override
    @Transactional
    public Persona actualizarPersona(Persona persona) {
        // Asegurarse de que la contraseña se maneje correctamente si se actualiza
        if (persona.getContraseña() != null && !persona.getContraseña().isEmpty()) {
            persona.setContraseña(bCryptPasswordEncoder.encode(persona.getContraseña()));
        } else {
            // Si la contraseña no se proporciona en la actualización, mantener la existente
            personaRepository.findById(persona.getIdPersona()).ifPresent(existingPersona ->
                persona.setContraseña(existingPersona.getContraseña())
            );
        }
        return personaRepository.save(persona);
    }

    @Override
    @Transactional
    public void eliminarPersona(Long id) {
        personaRepository.deleteById(id);
    }

    @Override
    public void autenticarPersona(String mail, String contraseña) {
        // Este método ahora delega en el AuthenticationManager de Spring Security
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(mail, contraseña)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}