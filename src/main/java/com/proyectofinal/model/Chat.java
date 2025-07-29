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

    @Column(name = "nombreChat")
    private String nombreChat;

    @Column(name = "id_arrendador", nullable = false) // NIF del arrendador
    private String idArrendador;

    @Column(name = "fechaCreacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "fechaEliminacion")
    private LocalDateTime fechaEliminacion;

    // Relación ManyToMany con Persona a través de ChatParticipantes
    @ManyToMany
    @JoinTable(
        name = "chat_participantes",
        joinColumns = @JoinColumn(name = "id_chat"),
        inverseJoinColumns = @JoinColumn(name = "id_persona")
    )
    private Set<Persona> participantes = new HashSet<>();


    // Constructor por defecto (necesario para JPA)
    public Chat() {
    }

    // Constructor con todos los campos (sin ID)
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

    public Set<Persona> getParticipantes() {
        return participantes;
    }

    public void setParticipantes(Set<Persona> participantes) {
        this.participantes = participantes;
    }

    public void addParticipante(Persona persona) {
        this.participantes.add(persona);
    }

    public void removeParticipante(Persona persona) {
        this.participantes.remove(persona);
    }
}