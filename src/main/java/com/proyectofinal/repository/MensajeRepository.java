/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.proyectofinal.repository;

/**
 *
 * @author antoine
 */

import com.proyectofinal.model.Mensaje;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Interfaz de repositorio para la entidad Mensaje.
 * Extiende JpaRepository para proporcionar operaciones CRUD básicas
 * y métodos de consulta personalizados para Mensaje.
 */
public interface MensajeRepository extends JpaRepository<Mensaje, Long> {
    /**
     * Busca mensajes por el ID del chat al que pertenecen.
     * Los resultados se ordenan por fecha de envío de forma ascendente.
     * @param idChat El ID del chat.
     * @return Una lista de mensajes del chat, ordenados por fecha de envío.
     */
    List<Mensaje> findByChat_IdChatOrderByFechaEnvioAsc(Long idChat);
}
