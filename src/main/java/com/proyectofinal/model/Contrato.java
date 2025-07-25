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
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "Contratos")
public class Contrato implements Serializable { // Implementar Serializable
    private static final long serialVersionUID = 1L; // UID para Serializable

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idContrato;

    @Column(nullable = false)
    private BigDecimal precio;

    private String duracion;
    private String tipo;

    @Column(nullable = false)
    private LocalDate fechaInicio;

    @Column(nullable = false)
    private LocalDate fechaFinal;

    @ManyToOne // Un contrato pertenece a una ubicación
    @JoinColumn(name = "idUbicacion", nullable = false) // Nombre de la columna FK en Contratos
    private Ubicacion ubicacion;

    // Constructor sin argumentos
    public Contrato() {
    }

    // Constructor con todos los argumentos
    public Contrato(Long idContrato, BigDecimal precio, String duracion, String tipo, LocalDate fechaInicio, LocalDate fechaFinal, Ubicacion ubicacion) {
        this.idContrato = idContrato;
        this.precio = precio;
        this.duracion = duracion;
        this.tipo = tipo;
        this.fechaInicio = fechaInicio;
        this.fechaFinal = fechaFinal;
        this.ubicacion = ubicacion;
    }

    // Getters y Setters
    public Long getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(Long idContrato) {
        this.idContrato = idContrato;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(LocalDate fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }
}

