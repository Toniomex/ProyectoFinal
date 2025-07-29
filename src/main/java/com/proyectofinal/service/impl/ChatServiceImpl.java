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
import com.proyectofinal.service.ChatService;
import com.proyectofinal.service.PersonaService; // Para obtener la Persona
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private PersonaService personaService; // Para obtener la Persona por ID

    @Override
    @Transactional(readOnly = true)
    public Optional<Chat> obtenerChatPorIdArrendador(String idArrendador) {
        return chatRepository.findByIdArrendador(idArrendador);
    }

    @Override
    @Transactional
    public Chat crearOObtenerChat(String idArrendador, String nombreChat, Persona inquilino) {
        Optional<Chat> existingChat = chatRepository.findByIdArrendador(idArrendador);
        Chat chat;
        if (existingChat.isPresent()) {
            chat = existingChat.get();
            System.out.println("DEBUG: Chat existente encontrado para NIF " + idArrendador + ": " + chat.getNombreChat());
        } else {
            chat = new Chat(null, nombreChat, idArrendador, LocalDateTime.now(), null);
            chat = chatRepository.save(chat);
            System.out.println("DEBUG: Nuevo chat creado para NIF " + idArrendador + ": " + chat.getNombreChat());
        }

        // Asegurarse de que el inquilino esté en la lista de participantes del chat
        if (!chat.getParticipantes().contains(inquilino)) {
            chat.addParticipante(inquilino);
            chat = chatRepository.save(chat); // Guardar el chat con el nuevo participante
            System.out.println("DEBUG: Inquilino " + inquilino.getNombre() + " añadido al chat " + chat.getNombreChat());
        }
        return chat;
    }

    @Override
    @Transactional
    public Chat agregarInquilinoAChat(Long idChat, Long idPersona) {
        Chat chat = chatRepository.findById(idChat)
                .orElseThrow(() -> new RuntimeException("Chat no encontrado con ID: " + idChat));
        Persona persona = personaService.obtenerPersonaPorId(idPersona)
                .orElseThrow(() -> new RuntimeException("Persona no encontrada con ID: " + idPersona));

        if (!chat.getParticipantes().contains(persona)) {
            chat.addParticipante(persona);
            chatRepository.save(chat);
        }
        return chat;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Chat> obtenerTodosLosChats() {
        return chatRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Chat> obtenerChatPorId(Long idChat) {
        return chatRepository.findById(idChat);
    }
}
