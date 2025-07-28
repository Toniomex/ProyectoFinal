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
import java.util.List;
import java.util.Optional;

/**
 * Interfaz de servicio para la gestión de Contratos.
 * Define las operaciones de negocio relacionadas con los contratos de alquiler.
 */
public interface ContratoService {
    Contrato crearContrato(Contrato contrato);
    Optional<Contrato> obtenerContratoPorId(Long id);
    List<Contrato> obtenerContratosPorInquilino(Long idInquilino);
    Contrato actualizarContrato(Contrato contrato);
    void eliminarContrato(Long id);
}
