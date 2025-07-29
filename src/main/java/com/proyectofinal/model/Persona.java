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

@Entity
@Table(name = "Personas")
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_persona")
    private Long idPersona;

    @Column(name = "nombre", length = 75, nullable = false)
    private String nombre;

    @Column(name = "mail", length = 200, unique = true, nullable = false)
    private String mail;

    @Column(name = "telefono", length = 20)
    private String telefono;

    @Column(name = "contraseña", length = 255, nullable = false) // Almacena el hash
    private String contraseña;

    @Enumerated(EnumType.STRING)
    @Column(name = "rol", nullable = false)
    private Rol rol;

    public enum Rol {
        INQUILINO,
        ADMINISTRADOR
    }

    // Constructor por defecto (necesario para JPA)
    public Persona() {
    }

    // Constructor con todos los campos (sin ID, ya que es auto-generado)
    public Persona(Long idPersona, String nombre, String mail, String telefono, String contraseña, Rol rol) {
        this.idPersona = idPersona;
        this.nombre = nombre;
        this.mail = mail;
        this.telefono = telefono;
        this.contraseña = contraseña;
        this.rol = rol;
    }

    // Getters y Setters
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    @Override
    public String toString() {
        return "Persona{" +
               "idPersona=" + idPersona +
               ", nombre='" + nombre + '\'' +
               ", mail='" + mail + '\'' +
               ", telefono='" + telefono + '\'' +
               ", rol=" + rol +
               '}';
    }
}