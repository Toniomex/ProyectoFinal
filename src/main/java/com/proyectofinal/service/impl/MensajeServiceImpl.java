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
import com.proyectofinal.model.Mensaje;
import com.proyectofinal.model.Persona;
import com.proyectofinal.repository.MensajeRepository;
import com.proyectofinal.service.ChatService; // Para obtener el Chat
import com.proyectofinal.service.MensajeService;
import com.proyectofinal.service.PersonaService; // Para obtener la Persona
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MensajeServiceImpl implements MensajeService {

    @Autowired
    private MensajeRepository mensajeRepository;

    @Autowired
    private ChatService chatService; // Necesario para obtener el objeto Chat

    @Autowired
    private PersonaService personaService; // Necesario para obtener el objeto Persona (remitente)

    @Override
    @Transactional
    public Mensaje enviarMensaje(Long idChat, Long idPersona, String contenido) {
        // Obtener el Chat y la Persona (remitente) a partir de sus IDs
        Chat chat = chatService.obtenerChatPorId(idChat)
                .orElseThrow(() -> new RuntimeException("Chat no encontrado con ID: " + idChat));
        Persona remitente = personaService.obtenerPersonaPorId(idPersona)
                .orElseThrow(() -> new RuntimeException("Persona (remitente) no encontrada con ID: " + idPersona));

        // Crear el objeto Mensaje
        Mensaje mensaje = new Mensaje(null, contenido, LocalDateTime.now(), null, chat, remitente);
        return mensajeRepository.save(mensaje);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Mensaje> obtenerMensajePorId(Long id) {
        return mensajeRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Mensaje> obtenerMensajesPorChat(Long idChat) {
        Chat chat = chatService.obtenerChatPorId(idChat)
                .orElseThrow(() -> new RuntimeException("Chat no encontrado con ID: " + idChat));
        // Asegurarse de que el método findByChatOrderByFechaEnvioAsc exista en MensajeRepository
        return mensajeRepository.findByChatOrderByFechaEnvioAsc(chat);
    }

    @Override
    @Transactional
    public void eliminarMensaje(Long id) {
        mensajeRepository.deleteById(id);
    }
}
