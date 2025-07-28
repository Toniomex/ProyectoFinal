/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyectofinal.service;

/**
 *
 * @author antoine
 */
import com.proyectofinal.model.Chat;
import com.proyectofinal.model.Persona;
import java.util.Optional;

/**
 * Interfaz de servicio para la gestión de Chats.
 * Define las operaciones de negocio relacionadas con los chats grupales.
 */
public interface ChatService {
    Optional<Chat> obtenerChatPorNifArrendador(String nifArrendador);
    Chat crearChat(String nifArrendador, String nombreChat);
    void añadirInquilinoAchat(Long idChat, Persona inquilino); // Cambiado a Persona para consistencia
    Optional<Chat> obtenerChatPorId(Long idChat); // Añadido para el DemoRunner
}
