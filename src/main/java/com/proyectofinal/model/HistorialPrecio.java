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
@Table(name = "HistorialPrecios")
public class HistorialPrecio implements Serializable { // Implementar Serializable
    private static final long serialVersionUID = 1L; // UID para Serializable

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idHistorialPrecio;

    @ManyToOne // Muchos registros de historial pueden pertenecer a un contrato
    @JoinColumn(name = "idContrato", nullable = false)
    private Contrato contrato;

    @Column(nullable = false)
    private BigDecimal precio;

    @Column(nullable = false)
    private LocalDate fechaAplicacion;

    // Constructor sin argumentos
    public HistorialPrecio() {
    }

    // Constructor con todos los argumentos
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