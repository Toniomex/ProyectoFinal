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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; // Importar BCryptPasswordEncoder
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Implementación de UserDetailsService para cargar los detalles del usuario
 * desde la base de datos para la autenticación de Spring Security.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private PersonaRepository personaRepository;

    // No se inyecta BCryptPasswordEncoder aquí si se usa en SecurityConfig para AuthenticationManager
    // Si se inyecta aquí, se debe asegurar que no haya un ciclo de dependencia.
    // En este caso, el PasswordEncoder se pasa directamente al AuthenticationManagerBuilder en SecurityConfig.

    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        Persona persona = personaRepository.findByMail(mail)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con mail: " + mail));

        // Construir UserDetails con el rol de la persona
        return new User(persona.getMail(), persona.getContraseña(), new ArrayList<>());
    }
}