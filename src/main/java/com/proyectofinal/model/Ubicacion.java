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
@Table(name = "Ubicaciones")
public class Ubicacion implements Serializable { // Implementar Serializable
    private static final long serialVersionUID = 1L; // UID para Serializable

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUbicacion;

    @Column(nullable = false)
    private String calle;
    private String numeroCalle;
    private String piso;
    private String escalera;
    private String puerta;
    private String otro;

    @Column(nullable = false)
    private String codigoPostal;
    @Column(nullable = false)
    private String ciudad;
    @Column(nullable = false)
    private String provincia;
    @Column(nullable = false)
    private String pais;

    // Constructor sin argumentos
    public Ubicacion() {
    }

    // Constructor con todos los argumentos
    public Ubicacion(Long idUbicacion, String calle, String numeroCalle, String piso, String escalera, String puerta, String otro, String codigoPostal, String ciudad, String provincia, String pais) {
        this.idUbicacion = idUbicacion;
        this.calle = calle;
        this.numeroCalle = numeroCalle;
        this.piso = piso;
        this.escalera = escalera;
        this.puerta = puerta;
        this.otro = otro;
        this.codigoPostal = codigoPostal;
        this.ciudad = ciudad;
        this.provincia = provincia;
        this.pais = pais;
    }

    // Getters y Setters
    public Long getIdUbicacion() {
        return idUbicacion;
    }

    public void setIdUbicacion(Long idUbicacion) {
        this.idUbicacion = idUbicacion;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumeroCalle() {
        return numeroCalle;
    }

    public void setNumeroCalle(String numeroCalle) {
        this.numeroCalle = numeroCalle;
    }

    public String getPiso() {
        return piso;
    }

    public void setPiso(String piso) {
        this.piso = piso;
    }

    public String getEscalera() {
        return escalera;
    }

    public void setEscalera(String escalera) {
        this.escalera = escalera;
    }

    public String getPuerta() {
        return puerta;
    }

    public void setPuerta(String puerta) {
        this.puerta = puerta;
    }

    public String getOtro() {
        return otro;
    }

    public void setOtro(String otro) {
        this.otro = otro;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }
}
