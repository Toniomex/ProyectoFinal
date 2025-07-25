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
import com.proyectofinal.model.ContratoParticipante;
import com.proyectofinal.model.Persona;
import com.proyectofinal.model.TipoParticipante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContratoParticipanteRepository extends JpaRepository<ContratoParticipante, Long> {
    // Buscar participantes por contrato y tipo (inquilino/arrendador)
    List<ContratoParticipante> findByContratoAndTipoParticipante(Contrato contrato, TipoParticipante tipoParticipante);

    // Buscar participantes por NIF de arrendador
    List<ContratoParticipante> findByNIFArrendador(String NIFArrendador);

    // Buscar participantes por Persona (para inquilinos)
    List<ContratoParticipante> findByPersona(Persona persona);

    // Buscar un participante específico por contrato y persona
    Optional<ContratoParticipante> findByContratoAndPersona(Contrato contrato, Persona persona);
}
