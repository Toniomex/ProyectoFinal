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
import java.io.Serializable; // Añadir Serializable

@Entity
@Table(name = "ContratoParticipantes")
public class ContratoParticipante implements Serializable { // Implementar Serializable
    private static final long serialVersionUID = 1L; // UID para Serializable

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idContratoParticipante;

    @ManyToOne // Muchos participantes pueden estar en un contrato
    @JoinColumn(name = "idContrato", nullable = false)
    private Contrato contrato;

    @ManyToOne // Un participante puede ser una Persona (inquilino)
    @JoinColumn(name = "idPersona", nullable = true) // idPersona puede ser NULL para arrendadores
    private Persona persona; // Solo para inquilinos

    private String NIFArrendador; // Solo para arrendadores (puede ser NULL para inquilinos)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoParticipante tipoParticipante; // inquilino o arrendador

    // Constructor sin argumentos
    public ContratoParticipante() {
    }

    // Constructor con todos los argumentos
    public ContratoParticipante(Long idContratoParticipante, Contrato contrato, Persona persona, String NIFArrendador, TipoParticipante tipoParticipante) {
        this.idContratoParticipante = idContratoParticipante;
        this.contrato = contrato;
        this.persona = persona;
        this.NIFArrendador = NIFArrendador;
        this.tipoParticipante = tipoParticipante;
    }

    // Getters y Setters
    public Long getIdContratoParticipante() {
        return idContratoParticipante;
    }

    public void setIdContratoParticipante(Long idContratoParticipante) {
        this.idContratoParticipante = idContratoParticipante;
    }

    public Contrato getContrato() {
        return contrato;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public String getNIFArrendador() {
        return NIFArrendador;
    }

    public void setNIFArrendador(String NIFArrendador) {
        this.NIFArrendador = NIFArrendador;
    }

    public TipoParticipante getTipoParticipante() {
        return tipoParticipante;
    }

    public void setTipoParticipante(TipoParticipante tipoParticipante) {
        this.tipoParticipante = tipoParticipante;
    }
}