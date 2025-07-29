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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Controlador REST para la gestión de Personas (usuarios).
 * Proporciona endpoints para el registro, autenticación y obtención de información de personas.
 */
@RestController
@RequestMapping("/api") // Prefijo para todas las rutas de este controlador
public class PersonaController {

    @Autowired
    private PersonaService personaService;

    /**
     * Endpoint para registrar una nueva persona (inquilino/administrador).
     * @param persona El objeto Persona a registrar.
     * @return ResponseEntity con la persona registrada y estado HTTP 201.
     */
    @PostMapping("/register")
    public ResponseEntity<Persona> registerPersona(@RequestBody Persona persona) {
        try {
            Persona nuevaPersona = personaService.registrarPersona(persona);
            return new ResponseEntity<>(nuevaPersona, HttpStatus.CREATED);
        } catch (Exception e) {
            // Manejo de errores, por ejemplo, si el email ya existe
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Endpoint para autenticar una persona.
     * @param loginRequest Objeto que contiene el mail y la contraseña para el login.
     * @return ResponseEntity con la persona autenticada y estado HTTP 200, o 401 si falla la autenticación.
     */
    @PostMapping("/login")
    public ResponseEntity<Persona> loginPersona(@RequestBody LoginRequest loginRequest) {
        Optional<Persona> persona = personaService.autenticarPersona(loginRequest.getMail(), loginRequest.getContraseña());
        return persona.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                       .orElseGet(() -> new ResponseEntity<>(HttpStatus.UNAUTHORIZED));
    }

    /**
     * Endpoint para obtener una persona por su ID.
     * @param id El ID de la persona.
     * @return ResponseEntity con la persona y estado HTTP 200, o 404 si no se encuentra.
     */
    @GetMapping("/personas/{id}")
    public ResponseEntity<Persona> getPersonaById(@PathVariable Long id) {
        Optional<Persona> persona = personaService.obtenerPersonaPorId(id);
        return persona.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                       .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Clase auxiliar para el cuerpo de la petición de login
    // Definida como static para que pueda ser usada sin una instancia de PersonaController
    static class LoginRequest {
        private String mail;
        private String contraseña;

        public String getMail() { return mail; }
        public void setMail(String mail) { this.mail = mail; }
        public String getContraseña() { return contraseña; }
        public void setContraseña(String contraseña) { this.contraseña = contraseña; }
    }
}