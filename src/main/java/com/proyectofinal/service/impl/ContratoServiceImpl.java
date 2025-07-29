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
import com.proyectofinal.model.Persona;
import com.proyectofinal.model.Chat; // Importar Chat
import com.proyectofinal.repository.ContratoParticipanteRepository;
import com.proyectofinal.repository.ContratoRepository;
import com.proyectofinal.service.ContratoService;
import com.proyectofinal.service.PersonaService; // Para obtener la Persona por ID
import com.proyectofinal.service.ChatService; // Para interactuar con el servicio de chat
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ContratoServiceImpl implements ContratoService {

    @Autowired
    private ContratoRepository contratoRepository;

    @Autowired
    private ContratoParticipanteRepository contratoParticipanteRepository;

    @Autowired
    private PersonaService personaService; // Para buscar la persona inquilina

    @Autowired
    private ChatService chatService; // Para interactuar con el chat

    @Override
    @Transactional
    public Contrato crearContrato(Contrato contrato, Long inquilinoId, String nifArrendador) {
        // *** CORRECCIÓN IMPORTANTE 1: Establecer idArrendadorNIF antes de la primera llamada a save ***
        contrato.setIdArrendadorNIF(nifArrendador);

        // *** CORRECCIÓN IMPORTANTE 2: Obtener y establecer el inquilino antes de la primera llamada a save ***
        Persona inquilino = personaService.obtenerPersonaPorId(inquilinoId)
                                          .orElseThrow(() -> new RuntimeException("Inquilino no encontrado con ID: " + inquilinoId));
        contrato.setInquilino(inquilino);

        // 1. Guardar el contrato
        Contrato nuevoContrato = contratoRepository.save(contrato); // Ahora 'inquilino' y 'idArrendadorNIF' ya están establecidos

        // 2. Asociar al inquilino al contratoParticipante
        ContratoParticipante inquilinoParticipante = new ContratoParticipante(null, nuevoContrato, inquilino, null, ContratoParticipante.TipoParticipante.INQUILINO);
        contratoParticipanteRepository.save(inquilinoParticipante);
        System.out.println("DEBUG: ContratoParticipante creado para inquilino " + inquilino.getNombre() + " y contrato " + nuevoContrato.getIdContrato());

        // 3. Crear o verificar el chat para el arrendador y añadir al inquilino
        String chatNombre = "Chat Arrendador " + nifArrendador;
        Chat chat = chatService.crearOObtenerChat(nifArrendador, chatNombre, inquilino);
        System.out.println("DEBUG: Inquilino " + inquilino.getNombre() + " añadido/verificado en chat " + chat.getNombreChat());


        // 4. Asociar al arrendador (por NIF) al contratoParticipante
        ContratoParticipante arrendadorParticipante = new ContratoParticipante(null, nuevoContrato, null, nifArrendador, ContratoParticipante.TipoParticipante.ARRENDADOR);
        contratoParticipanteRepository.save(arrendadorParticipante);

        return nuevoContrato; // Retornar el contrato ya guardado
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Contrato> obtenerContratoPorId(Long id) {
        return contratoRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Contrato> obtenerTodosLosContratos() {
        return contratoRepository.findAll();
    }

    @Override
    @Transactional
    public Contrato actualizarContrato(Contrato contrato) {
        return contratoRepository.save(contrato);
    }

    @Override
    @Transactional
    public void eliminarContrato(Long id) {
        contratoRepository.deleteById(id);
    }
}