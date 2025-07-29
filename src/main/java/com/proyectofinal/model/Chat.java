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
import java.util.HashSet; // Importar HashSet
import java.util.Set;     // Importar Set

@Entity
@Table(name = "Chats")
public class Chat implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idChat;

    private String nombreChat;

    @Column(nullable = false)
    private String idArrendador;

    @Column(nullable = false)
    private LocalDateTime fechaCreacion;

    private LocalDateTime fechaEliminacion;

    // Relación Many-to-Many con Persona para los participantes del chat
    @ManyToMany
    @JoinTable(
        name = "chat_participantes", // Nombre de la tabla de unión
        joinColumns = @JoinColumn(name = "id_chat"), // Columna FK de esta entidad (Chat)
        inverseJoinColumns = @JoinColumn(name = "id_persona") // Columna FK de la entidad remota (Persona)
    )
    private Set<Persona> participantes = new HashSet<>(); // Inicializar para evitar NullPointerException

    // Constructor sin argumentos
    public Chat() {
    }

    // Constructor con todos los argumentos (sin participantes, se añaden con addParticipante)
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

    // Método de utilidad para añadir un participante
    public void addParticipante(Persona persona) {
        this.participantes.add(persona);
    }

    // Método de utilidad para remover un participante
    public void removeParticipante(Persona persona) {
        this.participantes.remove(persona);
    }
}
