/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyectofinal.service;

/**
 *
 * @author antoine
 */
import com.proyectofinal.model.Contrato;
import com.proyectofinal.model.Persona;
import com.proyectofinal.model.Ubicacion;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ContratoService {
    Contrato crearContrato(Contrato contrato, Long inquilinoId, String nifArrendador);
    Optional<Contrato> obtenerContratoPorId(Long id);
    List<Contrato> obtenerTodosLosContratos();
    Contrato actualizarContrato(Contrato contrato);
    void eliminarContrato(Long id);
}