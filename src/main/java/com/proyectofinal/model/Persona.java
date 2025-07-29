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
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.Collections; // Importar Collections
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "Personas")
public class Persona implements UserDetails { // Implementa UserDetails

    public enum Rol {
        ADMINISTRADOR, INQUILINO; // Definir roles

        // Método para obtener la autoridad de Spring Security
        public String getAuthorityName() {
            return "ROLE_" + this.name();
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_persona")
    private Long idPersona;

    private String nombre;

    @Column(unique = true, nullable = false)
    private String mail; // Usaremos mail como nombre de usuario para Spring Security

    @Column(nullable = false)
    private String contraseña; // La contraseña hasheada

    private String telefono;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Rol rol;

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

    // --- Métodos de UserDetails ---
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Devuelve una colección de autoridades (roles) para esta persona
        return Collections.singletonList(new SimpleGrantedAuthority(rol.getAuthorityName()));
    }

    @Override
    public String getPassword() {
        return this.contraseña;
    }

    @Override
    public String getUsername() {
        return this.mail; // Usamos el email como nombre de usuario para Spring Security
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Siempre activa para esta demo
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Siempre no bloqueada para esta demo
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Siempre no expiradas para esta demo
    }

    @Override
    public boolean isEnabled() {
        return true; // Siempre habilitada para esta demo
    }
}
