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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; // Importar BCryptPasswordEncoder
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * CustomUserDetailsService: Implementación de UserDetailsService para cargar
 * los detalles del usuario (Persona) desde la base de datos para Spring Security.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder; // Inyectar BCryptPasswordEncoder

    /**
     * Carga los detalles del usuario por su nombre de usuario (email en este caso).
     * @param username El email del usuario.
     * @return UserDetails que representa al usuario.
     * @throws UsernameNotFoundException Si el usuario no es encontrado.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Buscar la Persona por su email (que es el "username" para Spring Security)
        Optional<Persona> personaOpt = personaRepository.findByMail(username);
        if (personaOpt.isEmpty()) {
            throw new UsernameNotFoundException("Usuario no encontrado con email: " + username);
        }
        return personaOpt.get(); // La entidad Persona ya implementa UserDetails
    }

    /**
     * Método para encriptar una contraseña usando BCrypt.
     * @param password La contraseña en texto plano.
     * @return La contraseña encriptada.
     */
    public String encriptarContraseña(String password) {
        return bCryptPasswordEncoder.encode(password);
    }

    /**
     * Método para autenticar un usuario manualmente (aunque Spring Security lo hace automáticamente).
     * Útil para verificar contraseñas fuera del flujo de autenticación estándar si es necesario.
     * @param email El email del usuario.
     * @param rawPassword La contraseña en texto plano.
     * @return true si la autenticación es exitosa, false en caso contrario.
     */
    public boolean authenticate(String email, String rawPassword) {
        Optional<Persona> personaOpt = personaRepository.findByMail(email);
        if (personaOpt.isPresent()) {
            Persona persona = personaOpt.get();
            // Compara la contraseña en texto plano con la hasheada
            return bCryptPasswordEncoder.matches(rawPassword, persona.getContraseña());
        }
        return false;
    }
}
