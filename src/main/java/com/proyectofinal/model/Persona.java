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
import java.util.Set; // Para la relación ManyToMany con Chat

@Entity
@Table(name = "Personas") // Asegúrate de que coincida con tu estrategia de nombres
public class Persona {

    public enum Rol {
        ADMINISTRADOR, INQUILINO
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_persona")
    private Long idPersona;

    private String nombre;

    @Column(unique = true, nullable = false)
    private String mail;

    @Column(nullable = false)
    private String contraseña; // En una app real, esto debe ser hasheado

    private String telefono;

    @Enumerated(EnumType.STRING) // Almacena el enum como String en la DB
    @Column(nullable = false)
    private Rol rol;

    // Relación ManyToMany con Chat (para participantes del chat)
    @ManyToMany(mappedBy = "participantes")
    private Set<Chat> chats;

    // --- Constructores ---
    public Persona() {
    }

    public Persona(String nombre, String mail, String contraseña, String telefono, Rol rol) {
        this.nombre = nombre;
        this.mail = mail;
        this.contraseña = contraseña;
        this.telefono = telefono;
        this.rol = rol;
    }

    // --- Getters y Setters ---
    public Long getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Long idPersona) {
        this.idPersona = idPersona;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public Set<Chat> getChats() {
        return chats;
    }

    public void setChats(Set<Chat> chats) {
        this.chats = chats;
    }

    @Override
    public String toString() {
        return "Persona{" +
               "idPersona=" + idPersona +
               ", nombre='" + nombre + '\'' +
               ", mail='" + mail + '\'' +
               ", rol=" + rol +
               '}';
    }
}