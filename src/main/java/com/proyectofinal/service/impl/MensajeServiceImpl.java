/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyectofinal.service.impl;

/**
 *
 * @author antoine
 */
import com.proyectofinal.model.Mensaje;
import com.proyectofinal.repository.MensajeRepository;
import com.proyectofinal.service.MensajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Importar Transactional

import java.util.List;
import java.util.Optional;

/**
 * Implementación del servicio para la gestión de Mensajes.
 * Contiene la lógica de negocio para enviar y recuperar mensajes de chat.
 */
@Service
public class MensajeServiceImpl implements MensajeService {

    @Autowired
    private MensajeRepository mensajeRepository;

    @Override
    @Transactional // Asegura que la operación de envío sea atómica
    public Mensaje enviarMensaje(Mensaje mensaje) {
        return mensajeRepository.save(mensaje);
    }

    @Override
    @Transactional(readOnly = true) // Añadido: Asegura que la carga de mensajes y sus relaciones sea dentro de una transacción
    public List<Mensaje> obtenerMensajesPorChat(Long idChat) {
        // Al estar dentro de una transacción, las entidades relacionadas (como 'remitente' en Mensaje)
        // se inicializarán cuando se acceda a ellas, incluso si son lazy-loaded.
        return mensajeRepository.findByChat_IdChatOrderByFechaEnvioAsc(idChat);
    }

    @Override
    public Optional<Mensaje> obtenerMensajePorId(Long id) {
        return mensajeRepository.findById(id);
    }
}