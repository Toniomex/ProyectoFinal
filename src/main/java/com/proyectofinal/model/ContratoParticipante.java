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
@Table(name = "ContratoParticipantes")
public class ContratoParticipante {

    public enum TipoParticipante {
        ARRENDADOR, INQUILINO
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_contrato_participante")
    private Long idContratoParticipante;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_contrato", nullable = false)
    private Contrato contrato;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_persona") // Puede ser nulo si el participante es solo un arrendador (por NIF)
    private Persona persona;

    private String nifArrendador; // Para identificar al arrendador en este contexto

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoParticipante tipoParticipante;

    // --- Constructores ---
    public ContratoParticipante() {
    }

    // --- Getters y Setters ---
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

    public String getNifArrendador() {
        return nifArrendador;
    }

    public void setNifArrendador(String nifArrendador) {
        this.nifArrendador = nifArrendador;
    }

    public TipoParticipante getTipoParticipante() {
        return tipoParticipante;
    }

    public void setTipoParticipante(TipoParticipante tipoParticipante) {
        this.tipoParticipante = tipoParticipante;
    }
}