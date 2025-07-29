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
import com.proyectofinal.repository.PersonaRepository;
import com.proyectofinal.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
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
    private PersonaRepository personaRepository;

    @Override // Asegura que se sobreescribe un método de la interfaz
    public Optional<Chat> obtenerChatPorNifArrendador(String nifArrendador) {
        return chatRepository.findByIdArrendador(nifArrendador);
    }

    @Override // Asegura que se sobreescribe un método de la interfaz
    @Transactional
    public Chat crearChat(String nifArrendador, String nombreChat) {
        Chat chat = new Chat();
        chat.setNombreChat(nombreChat);
        chat.setIdArrendador(nifArrendador);
        chat.setFechaCreacion(LocalDateTime.now());
        if (chat.getParticipantes() == null) {
            chat.setParticipantes(new HashSet<>());
        }
        return chatRepository.save(chat);
    }

    @Override // Asegura que se sobreescribe un método de la interfaz
    @Transactional
    public void añadirInquilinoAchat(Long idChat, Persona inquilino) {
        Optional<Chat> chatOptional = chatRepository.findById(idChat);
        if (chatOptional.isPresent()) {
            Chat chat = chatOptional.get();
            if (chat.getParticipantes() == null) {
                chat.setParticipantes(new HashSet<>());
            }

            boolean yaEsMiembro = chat.getParticipantes().stream()
                                    .anyMatch(p -> p.getIdPersona().equals(inquilino.getIdPersona()));

            if (!yaEsMiembro) {
                chat.getParticipantes().add(inquilino);
                chatRepository.save(chat);
                System.out.println("DEBUG: Inquilino " + inquilino.getNombre() + " añadido al chat " + chat.getNombreChat());
            } else {
                System.out.println("DEBUG: Inquilino " + inquilino.getNombre() + " ya es miembro del chat " + chat.getNombreChat());
            }
        } else {
            System.err.println("ERROR: No se encontró el chat con ID " + idChat + " para añadir al inquilino.");
        }
    }

    @Override // Asegura que se sobreescribe un método de la interfaz
    public Optional<Chat> obtenerChatPorId(Long idChat) {
        return chatRepository.findById(idChat);
    }
}