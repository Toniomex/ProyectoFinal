/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.proyectofinal.repository;

/**
 *
 * @author antoine
 */

import com.proyectofinal.model.Contrato;
import com.proyectofinal.model.HistorialPrecio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistorialPrecioRepository extends JpaRepository<HistorialPrecio, Long> {
    // Buscar historial de precios para un contrato específico
    List<HistorialPrecio> findByContratoOrderByFechaAplicacionDesc(Contrato contrato);
}