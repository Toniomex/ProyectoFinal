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
import java.time.LocalDateTime;

@Entity
@Table(name = "Mensajes")
public class Mensaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mensaje")
    private Long idMensaje;

    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String contenido;

    @Column(nullable = false)
    private LocalDateTime fechaEnvio;

    private LocalDateTime fechaEliminacion; // Para borrado lógico

    @ManyToOne(fetch = FetchType.LAZY) // Mantener LAZY para el chat, si no hay problemas
    @JoinColumn(name = "id_chat", nullable = false)
    private Chat chat;

    @ManyToOne(fetch = FetchType.EAGER) // CAMBIO CLAVE AQUÍ: Carga EAGER para la Persona
    @JoinColumn(name = "id_persona", nullable = false) // Remitente del mensaje
    private Persona persona; // Asegúrate de que este campo se llama 'persona' y no 'remitente' si es el caso en tu DB

    // --- Constructores ---
    public Mensaje() {
    }

    public Mensaje(Long idMensaje, String contenido, LocalDateTime fechaEnvio, LocalDateTime fechaEliminacion, Chat chat, Persona persona) {
        this.idMensaje = idMensaje;
        this.contenido = contenido;
        this.fechaEnvio = fechaEnvio;
        this.fechaEliminacion = fechaEliminacion;
        this.chat = chat;
        this.persona = persona;
    }

    // --- Getters y Setters ---
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

    public Persona getPersona() { // Getter para el campo 'persona'
        return persona;
    }

    public void setPersona(Persona persona) { // Setter para el campo 'persona'
        this.persona = persona;
    }
}
