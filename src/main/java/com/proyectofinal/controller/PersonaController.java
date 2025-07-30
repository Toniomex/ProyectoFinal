/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyectofinal.controller;
/**
 *
 * @author antoine
 */

import com.proyectofinal.model.Persona;
import com.proyectofinal.service.PersonaService;
import jakarta.servlet.http.HttpServletRequest; // Import para HttpServletRequest
import jakarta.servlet.http.HttpSession; // Import para HttpSession
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;

/**
 * Controlador REST para la gestión de Personas (usuarios).
 * Incluye endpoints para registro y autenticación.
 */
@RestController
@RequestMapping("/api") // Mantén la base /api para tus endpoints
public class PersonaController {

    @Autowired
    private PersonaService personaService;

    @Autowired
    private AuthenticationManager authenticationManager; // Inyectar AuthenticationManager

    /**
     * Endpoint para el registro de nuevos usuarios (Inquilinos).
     * @param persona Los datos de la persona a registrar.
     * @return ResponseEntity con el estado del registro.
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerPersona(@RequestBody Persona persona) {
        try {
            // Asegurarse de que el rol sea INQUILINO por defecto si no se especifica
            if (persona.getRol() == null) {
                persona.setRol(Persona.Rol.INQUILINO);
            }
            Persona newPersona = personaService.registrarPersona(persona);
            // No devolver la contraseña en la respuesta
            Map<String, Object> response = new HashMap<>();
            response.put("idPersona", newPersona.getIdPersona());
            response.put("nombre", newPersona.getNombre());
            response.put("mail", newPersona.getMail());
            response.put("rol", newPersona.getRol());
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            // Manejar caso de email duplicado u otros errores de registro
            return new ResponseEntity<>(Map.of("message", "Error al registrar: " + e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Endpoint para el inicio de sesión de usuarios.
     * @param credentials Un mapa que contiene 'mail' y 'contraseña'.
     * @return ResponseEntity con los datos del usuario autenticado o un error.
     */
    @PostMapping("/login")
    public ResponseEntity<?> loginPersona(@RequestBody Map<String, String> credentials) {
        String mail = credentials.get("mail");
        String password = credentials.get("contraseña");

        try {
            // Autenticar usando el AuthenticationManager de Spring Security
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(mail, password)
            );
            // Establecer la autenticación en el contexto de seguridad
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Recuperar los detalles completos de la persona autenticada
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Persona persona = personaService.obtenerPersonaPorMail(userDetails.getUsername()) // Necesitarás este método en PersonaService
                                .orElseThrow(() -> new RuntimeException("Persona no encontrada después de autenticación."));

            // Devolver los datos del usuario (sin la contraseña)
            Map<String, Object> response = new HashMap<>();
            response.put("idPersona", persona.getIdPersona());
            response.put("nombre", persona.getNombre());
            response.put("mail", persona.getMail());
            response.put("rol", persona.getRol());
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            // Capturar cualquier excepción de autenticación (ej. BadCredentialsException, UsernameNotFoundException)
            return new ResponseEntity<>(Map.of("message", "Credenciales inválidas: " + e.getMessage()), HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Endpoint para cerrar sesión.
     * Invalida la sesión HTTP y limpia el contexto de seguridad.
     * @param request HttpServletRequest para acceder a la sesión.
     * @return ResponseEntity con el estado del logout.
     */
    @PostMapping("/logout")
    public ResponseEntity<?> logoutPersona(HttpServletRequest request) {
        SecurityContextHolder.clearContext(); // Limpia el contexto de seguridad
        HttpSession session = request.getSession(false); // Obtiene la sesión actual sin crear una nueva
        if (session != null) {
            session.invalidate(); // Invalida la sesión HTTP
        }
        return ResponseEntity.ok(Map.of("message", "Sesión cerrada correctamente."));
    }

    /**
     * Endpoint para obtener los detalles de una persona por ID.
     * Requiere autenticación.
     * @param id El ID de la persona.
     * @return ResponseEntity con los datos de la persona.
     */
    @GetMapping("/personas/{id}")
    public ResponseEntity<Persona> getPersonaById(@PathVariable Long id) {
        Optional<Persona> persona = personaService.obtenerPersonaPorId(id);
        return persona.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
