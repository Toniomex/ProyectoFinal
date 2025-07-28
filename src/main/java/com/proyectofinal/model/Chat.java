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
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Chats")
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_chat")
    private Long idChat;

    private String nombreChat;

    @Column(name = "id_arrendador", nullable = false) // NIF del arrendador asociado al chat
    private String idArrendador;

    @Column(nullable = false)
    private LocalDateTime fechaCreacion;

    private LocalDateTime fechaEliminacion; // Para borrado lógico

    // Relación ManyToMany con Persona para los participantes del chat
    @ManyToMany
    @JoinTable(
        name = "chat_participantes", // Nombre de la tabla intermedia
        joinColumns = @JoinColumn(name = "id_chat"),
        inverseJoinColumns = @JoinColumn(name = "id_persona")
    )
    private Set<Persona> participantes = new HashSet<>(); // Inicializar para evitar NullPointer

    // --- Constructores ---
    public Chat() {
    }

    // --- Getters y Setters ---
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

    public Set<Persona> getParticipantes() {
        return participantes;
    }

    public void setParticipantes(Set<Persona> participantes) {
        this.participantes = participantes;
    }

    // Método de conveniencia para añadir un participante
    public void addParticipante(Persona persona) {
        if (this.participantes == null) {
            this.participantes = new HashSet<>();
        }
        this.participantes.add(persona);
        persona.getChats().add(this); // Asegurar la bidireccionalidad
    }

    // Método de conveniencia para remover un participante
    public void removeParticipante(Persona persona) {
        if (this.participantes != null) {
            this.participantes.remove(persona);
            persona.getChats().remove(this); // Asegurar la bidireccionalidad
        }
    }
}