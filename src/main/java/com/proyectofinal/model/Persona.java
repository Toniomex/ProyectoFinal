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
import java.io.Serializable; // Añadir Serializable es una buena práctica para entidades JPA

@Entity // Indica que esta clase es una entidad JPA y se mapea a una tabla de DB
@Table(name = "Personas") // Especifica el nombre de la tabla en la base de datos
public class Persona implements Serializable { // Implementar Serializable
    private static final long serialVersionUID = 1L; // UID para Serializable

    @Id // Indica que este campo es la clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Configura la generación automática de IDs
    private Long idPersona; // Usar Long para IDs autoincrementales

    private String nombre;

    @Column(unique = true, nullable = false) // Asegura que el mail sea único y no nulo
    private String mail;

    private String telefono;

    @Column(nullable = false)
    private String contraseña; // Almacenará el hash de la contraseña

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Rol rol;

    // Constructor sin argumentos (requerido por JPA)
    public Persona() {
    }

    // Constructor con todos los argumentos
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
}
