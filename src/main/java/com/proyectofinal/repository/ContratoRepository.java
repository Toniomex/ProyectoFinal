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
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Interfaz de repositorio para la entidad Contrato.
 * Extiende JpaRepository para proporcionar operaciones CRUD básicas
 * y métodos de consulta personalizados para Contrato.
 */
public interface ContratoRepository extends JpaRepository<Contrato, Long> {
    /**
     * Busca contratos por el ID de la persona inquilina asociada.
     * @param idPersona El ID de la persona inquilina.
     * @return Una lista de contratos asociados a la persona.
     */
    List<Contrato> findByInquilino_IdPersona(Long idPersona);
}
