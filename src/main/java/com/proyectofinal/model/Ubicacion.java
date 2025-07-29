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
@Table(name = "Ubicaciones")
public class Ubicacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ubicacion")
    private Long idUbicacion;

    @Column(name = "calle", nullable = false)
    private String calle;

    @Column(name = "numeroCalle", length = 10)
    private String numeroCalle;

    @Column(name = "piso", length = 10)
    private String piso;

    @Column(name = "escalera", length = 10)
    private String escalera;

    @Column(name = "puerta", length = 10)
    private String puerta;

    @Column(name = "otro")
    private String otro;

    @Column(name = "codigoPostal", length = 10, nullable = false)
    private String codigoPostal;

    @Column(name = "ciudad", length = 100, nullable = false)
    private String ciudad;

    @Column(name = "provincia", length = 100, nullable = false)
    private String provincia;

    @Column(name = "pais", length = 100, nullable = false)
    private String pais;

    // Constructor por defecto (necesario para JPA)
    public Ubicacion() {
    }

    // Constructor con todos los campos (sin ID)
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