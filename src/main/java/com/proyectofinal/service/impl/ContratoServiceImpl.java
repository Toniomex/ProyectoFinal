/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyectofinal.service.impl;

/**
 *
 * @author antoine
 */

import com.proyectofinal.model.Contrato;
import com.proyectofinal.model.ContratoParticipante;
import com.proyectofinal.model.Chat;
import com.proyectofinal.model.Persona; // Importar Persona
import com.proyectofinal.repository.ContratoRepository;
import com.proyectofinal.repository.ContratoParticipanteRepository;
import com.proyectofinal.service.ContratoService;
import com.proyectofinal.service.ChatService; // Inyectar ChatService
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Importar Transactional

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Implementación del servicio para la gestión de Contratos.
 * Contiene la lógica de negocio para los contratos de alquiler,
 * incluyendo la creación/unión automática a chats.
 */
@Service
public class ContratoServiceImpl implements ContratoService {

    @Autowired
    private ContratoRepository contratoRepository;

    @Autowired
    private ContratoParticipanteRepository contratoParticipanteRepository;

    @Autowired
    private ChatService chatService; // Inyección de ChatService

    @Override
    @Transactional // Asegura que toda la operación sea atómica
    public Contrato crearContrato(Contrato contrato) {
        // 1. Guardar el contrato
        Contrato nuevoContrato = contratoRepository.save(contrato);

        // 2. Crear o encontrar el chat asociado al NIF del arrendador
        Optional<Chat> chatExistente = chatService.obtenerChatPorNifArrendador(contrato.getNifArrendador());
        Chat chat;
        if (chatExistente.isPresent()) {
            chat = chatExistente.get();
            System.out.println("DEBUG: Chat existente encontrado para NIF " + contrato.getNifArrendador() + ": " + chat.getNombreChat());
        } else {
            // Si no existe, crear un nuevo chat
            String nombreChat = "Chat Arrendador " + contrato.getNifArrendador();
            chat = chatService.crearChat(contrato.getNifArrendador(), nombreChat);
            System.out.println("DEBUG: Nuevo chat creado para NIF " + contrato.getNifArrendador() + ": " + chat.getNombreChat());
        }

        // 3. Crear ContratoParticipante para el inquilino y el contrato
        ContratoParticipante contratoParticipante = new ContratoParticipante();
        contratoParticipante.setContrato(nuevoContrato);
        contratoParticipante.setPersona(contrato.getInquilino()); // Asumiendo que el contrato ya tiene el objeto Persona del inquilino
        contratoParticipante.setNifArrendador(contrato.getNifArrendador()); // Guardar NIF en el participante también
        contratoParticipante.setTipoParticipante(ContratoParticipante.TipoParticipante.INQUILINO); // Asumiendo enum TipoParticipante
        contratoParticipanteRepository.save(contratoParticipante);
        System.out.println("DEBUG: ContratoParticipante creado para inquilino " + contrato.getInquilino().getNombre() + " y contrato " + nuevoContrato.getIdContrato());

        // 4. Añadir el inquilino al chat (si no está ya)
        // La lógica de chatService.añadirInquilinoAchat debería verificar si ya es miembro
        chatService.añadirInquilinoAchat(chat.getIdChat(), contrato.getInquilino());
        System.out.println("DEBUG: Inquilino " + contrato.getInquilino().getNombre() + " añadido/verificado en chat " + chat.getNombreChat());

        return nuevoContrato;
    }

    @Override
    public Optional<Contrato> obtenerContratoPorId(Long id) {
        return contratoRepository.findById(id);
    }

    @Override
    public List<Contrato> obtenerContratosPorInquilino(Long idInquilino) {
        return contratoRepository.findByInquilino_IdPersona(idInquilino);
    }

    @Override
    public Contrato actualizarContrato(Contrato contrato) {
        // Aquí podrías añadir lógica de validación o actualización específica
        return contratoRepository.save(contrato);
    }

    @Override
    public void eliminarContrato(Long id) {
        contratoRepository.deleteById(id);
    }
}
