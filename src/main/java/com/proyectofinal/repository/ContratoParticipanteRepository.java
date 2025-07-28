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
import com.proyectofinal.model.Contrato;
import com.proyectofinal.model.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

/**
 * Interfaz de repositorio para la entidad ContratoParticipante.
 * Extiende JpaRepository para proporcionar operaciones CRUD básicas
 * y métodos de consulta personalizados para ContratoParticipante.
 */
public interface ContratoParticipanteRepository extends JpaRepository<ContratoParticipante, Long> {
    /**
     * Busca participantes de contrato por el NIF del arrendador.
     * Esto es útil para encontrar todos los inquilinos asociados a un mismo arrendador.
     * @param nifArrendador El NIF del arrendador.
     * @return Una lista de ContratoParticipante asociados a ese NIF de arrendador.
     */
    // Corregido: El nombre del método debe coincidir con el nombre del campo en la entidad (nifArrendador)
    List<ContratoParticipante> findByNifArrendador(String nifArrendador);

    /**
     * Busca un ContratoParticipante específico por Contrato y Persona.
     * @param contrato El objeto Contrato.
     * @param persona El objeto Persona.
     * @return Un Optional que contiene el ContratoParticipante si se encuentra, o vacío si no.
     */
    Optional<ContratoParticipante> findByContratoAndPersona(Contrato contrato, Persona persona);
}
