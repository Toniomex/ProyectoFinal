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

/**
 * Interfaz de servicio para la gestión de Mensajes.
 * Define las operaciones de negocio relacionadas con los mensajes de chat.
 */
public interface MensajeService {
    Mensaje enviarMensaje(Mensaje mensaje);
    Optional<Mensaje> obtenerMensajePorId(Long id);
    List<Mensaje> obtenerMensajesPorChat(Long idChat);
    // Puedes añadir más métodos CRUD si los necesitas (ej. actualizar, eliminar)
}