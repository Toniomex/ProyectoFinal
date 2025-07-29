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
import java.util.List;
import java.util.Optional;

public interface ChatService {
    Optional<Chat> obtenerChatPorIdArrendador(String idArrendador);
    Chat crearOObtenerChat(String idArrendador, String nombreChat, Persona inquilino);
    Chat agregarInquilinoAChat(Long idChat, Long idPersona);
    List<Chat> obtenerTodosLosChats();
    Optional<Chat> obtenerChatPorId(Long idChat);
}
