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

@Entity
@Table(name = "ContratoParticipantes")
public class ContratoParticipante implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idContratoParticipante;

    @ManyToOne
    @JoinColumn(name = "idContrato", nullable = false)
    private Contrato contrato;

    @ManyToOne
    @JoinColumn(name = "idPersona", nullable = true)
    private Persona persona;

    private String NIFArrendador; // Campo con NIF en mayúsculas

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoParticipante tipoParticipante; // Referencia al enum global

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

    // Getter para NIFArrendador (NIF en mayúsculas)
    public String getNIFArrendador() {
        return NIFArrendador;
    }

    // Setter para NIFArrendador (NIF en mayúsculas)
    public void setNIFArrendador(String NIFArrendador) { // Corregido: NIF en mayúsculas
        this.NIFArrendador = NIFArrendador;
    }

    public TipoParticipante getTipoParticipante() {
        return tipoParticipante;
    }

    public void setTipoParticipante(TipoParticipante tipoParticipante) {
        this.tipoParticipante = tipoParticipante;
    }
}