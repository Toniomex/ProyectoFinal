/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyectofinal.service.impl;

/**
 *
 * @author antoine
 */
import com.proyectofinal.model.Chat;
import com.proyectofinal.model.Persona;
import com.proyectofinal.repository.ChatRepository;
import com.proyectofinal.repository.PersonaRepository; // Necesario para añadir inquilinos
import com.proyectofinal.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Importar Transactional

import java.time.LocalDateTime;
import java.util.HashSet; // Para manejar la lista de participantes de forma eficiente
import java.util.Optional;
import java.util.Set;

/**
 * Implementación del servicio para la gestión de Chats.
 * Contiene la lógica de negocio para crear, obtener y gestionar miembros de chats.
 */
@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private PersonaRepository personaRepository; // Para verificar la existencia de la persona

    @Override
    public Optional<Chat> obtenerChatPorNifArrendador(String nifArrendador) {
        return chatRepository.findByIdArrendador(nifArrendador);
    }

    @Override
    @Transactional
    public Chat crearChat(String nifArrendador, String nombreChat) {
        Chat chat = new Chat();
        chat.setNombreChat(nombreChat);
        chat.setIdArrendador(nifArrendador);
        chat.setFechaCreacion(LocalDateTime.now());
        // Inicializar la lista de participantes si no es nula por defecto en la entidad
        if (chat.getParticipantes() == null) {
            chat.setParticipantes(new HashSet<>());
        }
        return chatRepository.save(chat);
    }

    @Override
    @Transactional
    public void añadirInquilinoAchat(Long idChat, Persona inquilino) {
        Optional<Chat> chatOptional = chatRepository.findById(idChat);
        if (chatOptional.isPresent()) {
            Chat chat = chatOptional.get();
            // Asegurarse de que la colección de participantes no es nula
            if (chat.getParticipantes() == null) {
                chat.setParticipantes(new HashSet<>());
            }

            // Verificar si el inquilino ya es participante del chat
            boolean yaEsMiembro = chat.getParticipantes().stream()
                                    .anyMatch(p -> p.getIdPersona().equals(inquilino.getIdPersona()));

            if (!yaEsMiembro) {
                // Añadir el inquilino a la lista de participantes del chat
                chat.getParticipantes().add(inquilino);
                chatRepository.save(chat); // Guardar el chat para persistir el cambio en la relación
                System.out.println("DEBUG: Inquilino " + inquilino.getNombre() + " añadido al chat " + chat.getNombreChat());
            } else {
                System.out.println("DEBUG: Inquilino " + inquilino.getNombre() + " ya es miembro del chat " + chat.getNombreChat());
            }
        } else {
            System.err.println("ERROR: No se encontró el chat con ID " + idChat + " para añadir al inquilino.");
        }
    }

    @Override
    public Optional<Chat> obtenerChatPorId(Long idChat) {
        return chatRepository.findById(idChat);
    }
}