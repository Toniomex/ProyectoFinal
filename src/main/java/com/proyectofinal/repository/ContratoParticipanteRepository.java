/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.proyectofinal.repository;

/**
 *
 * @author antoine
 */
import com.proyectofinal.model.ContratoParticipante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContratoParticipanteRepository extends JpaRepository<ContratoParticipante, Long> {
    // Corregido: 'NIFArrendador' debe coincidir exactamente con el nombre de la propiedad en la entidad
    List<ContratoParticipante> findByNIFArrendador(String NIFArrendador);
}
