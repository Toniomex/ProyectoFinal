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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional; // Importar Transactional

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Clase que implementa CommandLineRunner para ejecutar código de demostración
 * al inicio de la aplicación Spring Boot.
 * Esta clase se encarga de registrar usuarios, crear ubicaciones, contratos,
 * chats y mensajes para probar las funcionalidades del backend.
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

    /**
     * Este método se ejecuta automáticamente cuando la aplicación Spring Boot se inicia.
     * Está anotado con @Transactional para asegurar que todas las operaciones de base de datos
     * dentro de este método se ejecuten como una única transacción y se guarden al finalizar.
     *
     * @param args Argumentos de línea de comandos (no utilizados en este caso).
     * @throws Exception Si ocurre algún error durante la ejecución de la demostración.
     */
    @Override
    @Transactional // ¡Importante! Asegura que todas las operaciones se guarden en la DB.
    public void run(String... args) throws Exception {
        System.out.println("\n--- Iniciando Demo de Funcionalidades del Backend ---");
        System.out.println("-----------------------------------------------------");

        // --- 1. Registrando Personas (Inquilinos) ---
        System.out.println("\n--- 1. Registrando Personas (Inquilinos) ---");
        Persona alice = personaService.registrarPersona(new Persona(null, "Alice Smith", "alice@example.com", "111222333", "password123", Persona.Rol.INQUILINO));
        System.out.println("✔ Inquilino registrado: " + alice.getNombre() + " (ID: " + alice.getIdPersona() + ")");

        Persona bob = personaService.registrarPersona(new Persona(null, "Bob Johnson", "bob@example.com", "444555666", "password456", Persona.Rol.INQUILINO));
        System.out.println("✔ Inquilino registrado: " + bob.getNombre() + " (ID: " + bob.getIdPersona() + ")");

        Persona charlie = personaService.registrarPersona(new Persona(null, "Charlie Brown", "charlie@example.com", "777888999", "password789", Persona.Rol.INQUILINO));
        System.out.println("✔ Inquilino registrado: " + charlie.getNombre() + " (ID: " + charlie.getIdPersona() + ")");

        // --- 2. Autenticando Persona (solo para probar el servicio de seguridad) ---
        System.out.println("\n--- 2. Autenticando Persona ---");
        try {
            personaService.autenticarPersona("alice@example.com", "password123");
            System.out.println("✔ Autenticación exitosa para: Alice Smith");
        } catch (Exception e) {
            System.err.println("✖ Error de autenticación para Alice Smith: " + e.getMessage());
        }

        // --- 3. Creando Ubicaciones ---
        System.out.println("\n--- 3. Creando Ubicaciones ---");
        Ubicacion ubicacion1 = ubicacionService.guardarUbicacion(new Ubicacion(null, "Calle Falsa", "123", null, null, null, null, "08001", "Barcelona", "Barcelona", "España"));
        System.out.println("✔ Ubicación creada: " + ubicacion1.getCalle() + " (ID: " + ubicacion1.getIdUbicacion() + ")");

        Ubicacion ubicacion2 = ubicacionService.guardarUbicacion(new Ubicacion(null, "Avenida Siempre Viva", "742", null, null, null, null, "28001", "Madrid", "Madrid", "España"));
        System.out.println("✔ Ubicación creada: " + ubicacion2.getCalle() + " (ID: " + ubicacion2.getIdUbicacion() + ")");

        // --- 4. Creando Contratos y verificando Chats ---
        System.out.println("\n--- 4. Creando Contratos y verificando Chats ---");
        // Contrato para Alice con arrendador A12345678
        Contrato contrato1 = contratoService.crearContrato(new Contrato(null, new BigDecimal("1000.00"), null, "Vivienda", LocalDate.of(2023, 1, 1), LocalDate.of(2024, 1, 1), ubicacion1), alice.getIdPersona(), "A12345678");
        System.out.println("✔ Contrato 1 creado para Alice Smith con arrendador A12345678 (ID: " + contrato1.getIdContrato() + ")");

        // Contrato para Bob con el mismo arrendador A12345678
        Contrato contrato2 = contratoService.crearContrato(new Contrato(null, new BigDecimal("1100.00"), null, "Vivienda", LocalDate.of(2023, 3, 1), LocalDate.of(2024, 3, 1), ubicacion1), bob.getIdPersona(), "A12345678");
        System.out.println("✔ Contrato 2 creado para Bob Johnson con arrendador A12345678 (ID: " + contrato2.getIdContrato() + ")");

        // Contrato para Charlie con un nuevo arrendador B98765432
        Contrato contrato3 = contratoService.crearContrato(new Contrato(null, new BigDecimal("950.00"), null, "Local", LocalDate.of(2024, 1, 1), LocalDate.of(2025, 1, 1), ubicacion2), charlie.getIdPersona(), "B98765432");
        System.out.println("✔ Contrato 3 creado para Charlie Brown con arrendador B98765432 (ID: " + contrato3.getIdContrato() + ")");

        // --- Verificando Chats Creados/Unidos ---
        Optional<Chat> chatA = chatService.obtenerChatPorIdArrendador("A12345678");
        chatA.ifPresent(chat -> System.out.println("✔ Chat encontrado para arrendador A12345678: " + chat.getNombreChat() + " (ID: " + chat.getIdChat() + ")"));

        Optional<Chat> chatB = chatService.obtenerChatPorIdArrendador("B98765432");
        chatB.ifPresent(chat -> System.out.println("✔ Chat encontrado para arrendador B98765432: " + chat.getNombreChat() + " (ID: " + chat.getIdChat() + ")"));

        // --- 5. Enviando Mensajes ---
        System.out.println("\n--- 5. Enviando Mensajes ---");
        if (chatA.isPresent()) {
            Mensaje msgAlice = mensajeService.enviarMensaje(chatA.get().getIdChat(), alice.getIdPersona(), "Hola a todos, soy Alice! Este es el chat del arrendador A.");
            System.out.println("✔ Mensaje de Alice enviado al chat " + chatA.get().getNombreChat());

            Mensaje msgBob = mensajeService.enviarMensaje(chatA.get().getIdChat(), bob.getIdPersona(), "Qué tal, soy Bob. Un gusto estar aquí en el chat de A.");
            System.out.println("✔ Mensaje de Bob enviado al chat " + chatA.get().getNombreChat());
        }

        if (chatB.isPresent()) {
            Mensaje msgCharlie = mensajeService.enviarMensaje(chatB.get().getIdChat(), charlie.getIdPersona(), "Saludos desde el chat del arrendador B, soy Charlie.");
            System.out.println("✔ Mensaje de Charlie enviado al chat " + chatB.get().getNombreChat());
        }

        // --- 6. Recuperando Mensajes ---
        System.out.println("\n--- 6. Recuperando Mensajes ---");
        if (chatA.isPresent()) {
            System.out.println("Mensajes del chat " + chatA.get().getNombreChat() + ":");
            mensajeService.obtenerMensajesPorChat(chatA.get().getIdChat()).forEach(msg ->
                System.out.println("  [" + msg.getFechaEnvio().withNano(0) + "] " + msg.getRemitente().getNombre() + ": " + msg.getContenido())
            );
        }

        if (chatB.isPresent()) {
            System.out.println("Mensajes del chat " + chatB.get().getNombreChat() + ":");
            mensajeService.obtenerMensajesPorChat(chatB.get().getIdChat()).forEach(msg ->
                System.out.println("  [" + msg.getFechaEnvio().withNano(0) + "] " + msg.getRemitente().getNombre() + ": " + msg.getContenido())
            );
        }

        System.out.println("\n--- Demo de Funcionalidades del Backend Finalizada ---");

        // Pequeña pausa para asegurar que la transacción se complete antes de que la aplicación se apague
        // Esto es más un truco para la depuración en entornos de desarrollo rápido.
        // En una aplicación real, el servidor se mantendría en ejecución.
        try {
            Thread.sleep(2000); // Espera 2 segundos
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
