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
import com.proyectofinal.model.Ubicacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContratoRepository extends JpaRepository<Contrato, Long> {
    // Puedes añadir métodos personalizados si los necesitas, por ejemplo:
    List<Contrato> findByUbicacion(Ubicacion ubicacion);
}