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
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "HistorialPrecios")
public class HistorialPrecio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idHistorialPrecio")
    private Long idHistorialPrecio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idContrato", nullable = false)
    private Contrato contrato;

    @Column(name = "precio", precision = 10, scale = 2, nullable = false)
    private BigDecimal precio;

    @Column(name = "fechaAplicacion", nullable = false)
    private LocalDate fechaAplicacion;

    // Constructor por defecto (necesario para JPA)
    public HistorialPrecio() {
    }

    // Constructor con todos los campos (sin ID)
    public HistorialPrecio(Long idHistorialPrecio, Contrato contrato, BigDecimal precio, LocalDate fechaAplicacion) {
        this.idHistorialPrecio = idHistorialPrecio;
        this.contrato = contrato;
        this.precio = precio;
        this.fechaAplicacion = fechaAplicacion;
    }

    // Getters y Setters
    public Long getIdHistorialPrecio() {
        return idHistorialPrecio;
    }

    public void setIdHistorialPrecio(Long idHistorialPrecio) {
        this.idHistorialPrecio = idHistorialPrecio;
    }

    public Contrato getContrato() {
        return contrato;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public LocalDate getFechaAplicacion() {
        return fechaAplicacion;
    }

    public void setFechaAplicacion(LocalDate fechaAplicacion) {
        this.fechaAplicacion = fechaAplicacion;
    }
}
