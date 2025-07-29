/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyectofinal.service;

/**
 *
 * @author antoine
 */

import com.proyectofinal.model.Mensaje;
import java.util.List;
import java.util.Optional;

public interface MensajeService {
    Mensaje enviarMensaje(Long idChat, Long idPersona, String contenido); // Cambiado para recibir IDs y contenido
    Optional<Mensaje> obtenerMensajePorId(Long id);
    List<Mensaje> obtenerMensajesPorChat(Long idChat);
    void eliminarMensaje(Long id);
}