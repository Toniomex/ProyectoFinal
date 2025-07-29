/**
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
*/
package com.proyectofinal.runner;

/*
 *
 * @author antoine
 */

import com.proyectofinal.model.*;
import com.proyectofinal.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Clase DemoRunner:
 * Este componente se ejecuta automáticamente al iniciar la aplicación Spring Boot.
 * Su propósito es demostrar y verificar las funcionalidades clave del backend
 * (registro de usuarios, creación de contratos con lógica de chat,
 * envío y recuperación de mensajes) interactuando directamente con la capa de servicios.
 *
 * NOTA IMPORTANTE: Este código es para fines de demostración y prueba en desarrollo.
 * Debería ser eliminado o deshabilitado (ej. comentando @Component o usando perfiles de Spring)
 * antes de desplegar la aplicación en un entorno de producción, ya que creará datos
 * cada vez que la aplicación se inicie.
 */
@Component
public class DemoRunner implements CommandLineRunner {

    @Autowired
    private PersonaService personaService;

    @Autowired
    private UbicacionService ubicacionService;

    @Autowired
    private ContratoService contratoService;

    @Autowired
    private ChatService chatService;

    @Autowired
    private MensajeService mensajeService;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("\n--- Iniciando Demo de Funcionalidades del Backend ---");
        System.out.println("-----------------------------------------------------");

        // 1. Registrar Personas (Inquilinos)
        System.out.println("\n--- 1. Registrando Personas (Inquilinos) ---");
        // Las contraseñas se encriptarán automáticamente en PersonaService
        Persona inquilino1 = new Persona();
        inquilino1.setNombre("Alice Smith");
        inquilino1.setMail("alice@example.com");
        inquilino1.setContraseña("password123"); // Contraseña en texto plano
        inquilino1.setTelefono("111222333");
        inquilino1.setRol(Persona.Rol.INQUILINO);
        try {
            inquilino1 = personaService.registrarPersona(inquilino1);
            System.out.println("✔ Inquilino registrado: " + inquilino1.getNombre() + " (ID: " + inquilino1.getIdPersona() + ")");
        } catch (Exception e) {
            System.err.println("✖ Error al registrar inquilino1: " + e.getMessage());
            // Si ya existe, intentar recuperarlo para continuar la demo
            Optional<Persona> existingPersona = personaService.obtenerPersonaPorMail("alice@example.com");
            if (existingPersona.isPresent()) {
                inquilino1 = existingPersona.get();
                System.out.println("   (Inquilino Alice ya existía, recuperado: ID " + inquilino1.getIdPersona() + ")");
            } else {
                System.err.println("   (No se pudo registrar ni recuperar a Alice)");
                return;
            }
        }

        Persona inquilino2 = new Persona();
        inquilino2.setNombre("Bob Johnson");
        inquilino2.setMail("bob@example.com");
        inquilino2.setContraseña("securepass"); // Contraseña en texto plano
        inquilino2.setTelefono("444555666");
        inquilino2.setRol(Persona.Rol.INQUILINO);
        try {
            inquilino2 = personaService.registrarPersona(inquilino2);
            System.out.println("✔ Inquilino registrado: " + inquilino2.getNombre() + " (ID: " + inquilino2.getIdPersona() + ")");
        } catch (Exception e) {
            System.err.println("✖ Error al registrar inquilino2: " + e.getMessage());
            Optional<Persona> existingPersona = personaService.obtenerPersonaPorMail("bob@example.com");
            if (existingPersona.isPresent()) {
                inquilino2 = existingPersona.get();
                System.out.println("   (Inquilino Bob ya existía, recuperado: ID " + inquilino2.getIdPersona() + ")");
            } else {
                System.err.println("   (No se pudo registrar ni recuperar a Bob)");
                return;
            }
        }

        Persona inquilino3 = new Persona();
        inquilino3.setNombre("Charlie Brown");
        inquilino3.setMail("charlie@example.com");
        inquilino3.setContraseña("charliepass"); // Contraseña en texto plano
        inquilino3.setTelefono("777888999");
        inquilino3.setRol(Persona.Rol.INQUILINO);
        try {
            inquilino3 = personaService.registrarPersona(inquilino3);
            System.out.println("✔ Inquilino registrado: " + inquilino3.getNombre() + " (ID: " + inquilino3.getIdPersona() + ")");
        } catch (Exception e) {
            System.err.println("✖ Error al registrar inquilino3: " + e.getMessage());
            Optional<Persona> existingPersona = personaService.obtenerPersonaPorMail("charlie@example.com");
            if (existingPersona.isPresent()) {
                inquilino3 = existingPersona.get();
                System.out.println("   (Inquilino Charlie ya existía, recuperado: ID " + inquilino3.getIdPersona() + ")");
            } else {
                System.err.println("   (No se pudo registrar ni recuperar a Charlie)");
                return;
            }
        }

        // 2. Autenticar Persona (usando el método del servicio para verificar, no el de Spring Security directamente aquí)
        System.out.println("\n--- 2. Autenticando Persona ---");
        boolean isAuthenticated = personaService.autenticarPersona("alice@example.com", "password123").isPresent();
        if (isAuthenticated) {
            System.out.println("✔ Autenticación exitosa para: Alice Smith");
        } else {
            System.out.println("✖ Autenticación fallida para alice@example.com");
        }

        // 3. Crear Ubicaciones
        System.out.println("\n--- 3. Creando Ubicaciones ---");
        Ubicacion ubicacion1 = new Ubicacion();
        ubicacion1.setCalle("Calle Falsa");
        ubicacion1.setNumeroCalle("123");
        ubicacion1.setCiudad("Barcelona");
        ubicacion1.setProvincia("Barcelona");
        ubicacion1.setCodigoPostal("08001");
        ubicacion1.setPais("España");
        try {
            ubicacion1 = ubicacionService.guardarUbicacion(ubicacion1);
            System.out.println("✔ Ubicación creada: " + ubicacion1.getCalle() + " (ID: " + ubicacion1.getIdUbicacion() + ")");
        } catch (Exception e) {
            System.err.println("✖ Error al crear ubicación1: " + e.getMessage());
            return;
        }

        Ubicacion ubicacion2 = new Ubicacion();
        ubicacion2.setCalle("Avenida Siempre Viva");
        ubicacion2.setNumeroCalle("742");
        ubicacion2.setCiudad("Madrid");
        ubicacion2.setProvincia("Madrid");
        ubicacion2.setCodigoPostal("28001");
        ubicacion2.setPais("España");
        try {
            ubicacion2 = ubicacionService.guardarUbicacion(ubicacion2);
            System.out.println("✔ Ubicación creada: " + ubicacion2.getCalle() + " (ID: " + ubicacion2.getIdUbicacion() + ")");
        } catch (Exception e) {
            System.err.println("✖ Error al crear ubicación2: " + e.getMessage());
            return;
        }

        // 4. Crear Contratos (y verificar creación/unión de Chats)
        System.out.println("\n--- 4. Creando Contratos y verificando Chats ---");
        String nifArrendadorA = "A12345678";
        String nifArrendadorB = "B98765432";

        Contrato contrato1 = new Contrato();
        contrato1.setFechaInicio(LocalDate.of(2023, 1, 1));
        contrato1.setFechaFinal(LocalDate.of(2024, 1, 1));
        contrato1.setPrecio(new BigDecimal("1000.00"));
        contrato1.setTipo("Vivienda");
        contrato1.setUbicacion(ubicacion1);
        contrato1.setIdArrendadorNIF(nifArrendadorA); // Usar el nombre correcto del setter
        contrato1.setInquilino(inquilino1);
        try {
            contrato1 = contratoService.crearContrato(contrato1);
            System.out.println("✔ Contrato 1 creado para " + inquilino1.getNombre() + " con arrendador " + nifArrendadorA + " (ID: " + contrato1.getIdContrato() + ")");
        } catch (Exception e) {
            System.err.println("✖ Error al crear contrato1: " + e.getMessage());
        }

        Contrato contrato2 = new Contrato();
        contrato2.setFechaInicio(LocalDate.of(2023, 3, 1));
        contrato2.setFechaFinal(LocalDate.of(2024, 3, 1));
        contrato2.setPrecio(new BigDecimal("1100.00"));
        contrato2.setTipo("Vivienda");
        contrato2.setUbicacion(ubicacion1);
        contrato2.setIdArrendadorNIF(nifArrendadorA); // Usar el nombre correcto del setter
        contrato2.setInquilino(inquilino2);
        try {
            contrato2 = contratoService.crearContrato(contrato2);
            System.out.println("✔ Contrato 2 creado para " + inquilino2.getNombre() + " con arrendador " + nifArrendadorA + " (ID: " + contrato2.getIdContrato() + ")");
        } catch (Exception e) {
            System.err.println("✖ Error al crear contrato2: " + e.getMessage());
        }

        Contrato contrato3 = new Contrato();
        contrato3.setFechaInicio(LocalDate.of(2024, 1, 1));
        contrato3.setFechaFinal(LocalDate.of(2025, 1, 1));
        contrato3.setPrecio(new BigDecimal("950.00"));
        contrato3.setTipo("Local");
        contrato3.setUbicacion(ubicacion2);
        contrato3.setIdArrendadorNIF(nifArrendadorB); // Usar el nombre correcto del setter
        contrato3.setInquilino(inquilino3);
        try {
            contrato3 = contratoService.crearContrato(contrato3);
            System.out.println("✔ Contrato 3 creado para " + inquilino3.getNombre() + " con arrendador " + nifArrendadorB + " (ID: " + contrato3.getIdContrato() + ")");
        } catch (Exception e) {
            System.err.println("✖ Error al crear contrato3: " + e.getMessage());
        }

        System.out.println("\n--- Verificando Chats Creados/Unidos ---");
        Optional<Chat> chatA = chatService.obtenerChatPorNifArrendador(nifArrendadorA);
        if (chatA.isPresent()) {
            System.out.println("✔ Chat encontrado para arrendador " + nifArrendadorA + ": " + chatA.get().getNombreChat() + " (ID: " + chatA.get().getIdChat() + ")");
        } else {
            System.err.println("✖ ERROR: No se encontró chat para arrendador " + nifArrendadorA);
        }

        Optional<Chat> chatB = chatService.obtenerChatPorNifArrendador(nifArrendadorB);
        if (chatB.isPresent()) {
            System.out.println("✔ Chat encontrado para arrendador " + nifArrendadorB + ": " + chatB.get().getNombreChat() + " (ID: " + chatB.get().getIdChat() + ")");
        } else {
            System.err.println("✖ ERROR: No se encontró chat para arrendador " + nifArrendadorB);
        }

        // 5. Enviar Mensajes
        System.out.println("\n--- 5. Enviando Mensajes ---");
        if (chatA.isPresent()) {
            Mensaje mensaje1 = new Mensaje();
            mensaje1.setContenido("Hola a todos, soy Alice! Este es el chat del arrendador A.");
            mensaje1.setFechaEnvio(LocalDateTime.now());
            mensaje1.setChat(chatA.get());
            mensaje1.setPersona(inquilino1); // Usar setPersona para el campo 'persona'
            try {
                mensajeService.enviarMensaje(mensaje1);
                System.out.println("✔ Mensaje de Alice enviado al chat " + chatA.get().getNombreChat());
            } catch (Exception e) {
                System.err.println("✖ Error al enviar mensaje de Alice: " + e.getMessage());
            }

            Mensaje mensaje2 = new Mensaje();
            mensaje2.setContenido("Qué tal, soy Bob. Un gusto estar aquí en el chat de A.");
            mensaje2.setFechaEnvio(LocalDateTime.now().plusMinutes(1));
            mensaje2.setChat(chatA.get());
            mensaje2.setPersona(inquilino2); // Usar setPersona para el campo 'persona'
            try {
                mensajeService.enviarMensaje(mensaje2);
                System.out.println("✔ Mensaje de Bob enviado al chat " + chatA.get().getNombreChat());
            } catch (Exception e) {
                System.err.println("✖ Error al enviar mensaje de Bob: " + e.getMessage());
            }
        } else {
            System.err.println("✖ No se puede enviar mensajes al chat A, no se encontró.");
        }

        if (chatB.isPresent()) {
            Mensaje mensaje3 = new Mensaje();
            mensaje3.setContenido("Saludos desde el chat del arrendador B, soy Charlie.");
            mensaje3.setFechaEnvio(LocalDateTime.now());
            mensaje3.setChat(chatB.get());
            mensaje3.setPersona(inquilino3); // Usar setPersona para el campo 'persona'
            try {
                mensajeService.enviarMensaje(mensaje3);
                System.out.println("✔ Mensaje de Charlie enviado al chat " + chatB.get().getNombreChat());
            } catch (Exception e) {
                System.err.println("✖ Error al enviar mensaje de Charlie: " + e.getMessage());
            }
        } else {
            System.err.println("✖ No se puede enviar mensajes al chat B, no se encontró.");
        }

        // 6. Recuperar Mensajes
        System.out.println("\n--- 6. Recuperando Mensajes ---");
        if (chatA.isPresent()) {
            System.out.println("Mensajes del chat " + chatA.get().getNombreChat() + ":");
            List<Mensaje> mensajesChatA = mensajeService.obtenerMensajesPorChat(chatA.get().getIdChat());
            if (mensajesChatA.isEmpty()) {
                System.out.println("   (No hay mensajes en este chat)");
            } else {
                for (Mensaje msg : mensajesChatA) {
                    // Acceso a getPersona().getNombre() debería funcionar con FetchType.EAGER
                    System.out.println("  [" + msg.getFechaEnvio() + "] " + msg.getPersona().getNombre() + ": " + msg.getContenido());
                }
            }
        }

        if (chatB.isPresent()) {
            System.out.println("Mensajes del chat " + chatB.get().getNombreChat() + ":");
            List<Mensaje> mensajesChatB = mensajeService.obtenerMensajesPorChat(chatB.get().getIdChat());
            if (mensajesChatB.isEmpty()) {
                System.out.println("   (No hay mensajes en este chat)");
            } else {
                for (Mensaje msg : mensajesChatB) {
                    // Acceso a getPersona().getNombre() debería funcionar con FetchType.EAGER
                    System.out.println("  [" + msg.getFechaEnvio() + "] " + msg.getPersona().getNombre() + ": " + msg.getContenido());
                }
            }
        }

        System.out.println("\n--- Demo de Funcionalidades del Backend Finalizada ---");
        System.out.println("-----------------------------------------------------");
    }
}
