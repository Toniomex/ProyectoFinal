/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyectofinal.model;

/**
 *
 * @author antoine
 */

import jakarta.persistence.*;
import java.io.Serializable; 
import java.time.LocalDateTime;

@Entity
@Table(name = "Chats")
public class Chat implements Serializable { 
    private static final long serialVersionUID = 1L; 

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idChat;

    private String nombreChat;

    @Column(nullable = false)
    private String idArrendador; // NIF del arrendador asociado al chat

    @Column(nullable = false)
    private LocalDateTime fechaCreacion;

    private LocalDateTime fechaEliminacion; // Puede ser NULL si el chat no ha sido eliminado

    // Constructor sin argumentos
    public Chat() {
    }

    // Constructor con todos los argumentos
    public Chat(Long idChat, String nombreChat, String idArrendador, LocalDateTime fechaCreacion, LocalDateTime fechaEliminacion) {
        this.idChat = idChat;
        this.nombreChat = nombreChat;
        this.idArrendador = idArrendador;
        this.fechaCreacion = fechaCreacion;
        this.fechaEliminacion = fechaEliminacion;
    }

    // Getters y Setters
    public Long getIdChat() {
        return idChat;
    }

    public void setIdChat(Long idChat) {
        this.idChat = idChat;
    }

    public String getNombreChat() {
        return nombreChat;
    }

    public void setNombreChat(String nombreChat) {
        this.nombreChat = nombreChat;
    }

    public String getIdArrendador() {
        return idArrendador;
    }

    public void setIdArrendador(String idArrendador) {
        this.idArrendador = idArrendador;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDateTime getFechaEliminacion() {
        return fechaEliminacion;
    }

    public void setFechaEliminacion(LocalDateTime fechaEliminacion) {
        this.fechaEliminacion = fechaEliminacion;
    }
}