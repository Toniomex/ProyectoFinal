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
@Table(name = "Mensajes")
public class Mensaje implements Serializable { 
    private static final long serialVersionUID = 1L; 

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMensaje;

    @Column(nullable = false, columnDefinition = "LONGTEXT") // Mapea a LONGTEXT en MySQL
    private String contenido;

    @Column(nullable = false)
    private LocalDateTime fechaEnvio;

    private LocalDateTime fechaEliminacion; // Puede ser NULL

    @ManyToOne // Muchos mensajes pueden pertenecer a un chat
    @JoinColumn(name = "idChat", nullable = false)
    private Chat chat;

    @ManyToOne // Un mensaje es enviado por una Persona
    @JoinColumn(name = "idPersona", nullable = false)
    private Persona remitente; // Para identificar quién envió el mensaje

    // Constructor sin argumentos
    public Mensaje() {
    }

    // Constructor con todos los argumentos
    public Mensaje(Long idMensaje, String contenido, LocalDateTime fechaEnvio, LocalDateTime fechaEliminacion, Chat chat, Persona remitente) {
        this.idMensaje = idMensaje;
        this.contenido = contenido;
        this.fechaEnvio = fechaEnvio;
        this.fechaEliminacion = fechaEliminacion;
        this.chat = chat;
        this.remitente = remitente;
    }

    // Getters y Setters
    public Long getIdMensaje() {
        return idMensaje;
    }

    public void setIdMensaje(Long idMensaje) {
        this.idMensaje = idMensaje;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public LocalDateTime getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(LocalDateTime fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public LocalDateTime getFechaEliminacion() {
        return fechaEliminacion;
    }

    public void setFechaEliminacion(LocalDateTime fechaEliminacion) {
        this.fechaEliminacion = fechaEliminacion;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public Persona getRemitente() {
        return remitente;
    }

    public void setRemitente(Persona remitente) {
        this.remitente = remitente;
    }
}
