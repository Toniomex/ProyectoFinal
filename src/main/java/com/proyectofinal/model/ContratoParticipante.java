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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_contrato_participante")
    private Long idContratoParticipante;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_contrato", nullable = false)
    private Contrato contrato;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_persona") // Puede ser null si el participante es un arrendador por NIF
    private Persona persona;

    @Column(name = "nifArrendador", length = 255) // Solo para arrendadores, si no son Personas registradas
    private String NIFArrendador;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipoParticipante", nullable = false)
    private TipoParticipante tipoParticipante;

    public enum TipoParticipante {
        INQUILINO,
        ARRENDADOR
    }

    // Constructor por defecto (necesario para JPA)
    public ContratoParticipante() {
    }

    // Constructor con todos los campos (sin ID)
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
