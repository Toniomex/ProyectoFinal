/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.proyectofinal.repository;

/**
 *
 * @author antoine
 */
import com.proyectofinal.model.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository // Indica que esta interfaz es un componente de repositorio de Spring
public interface PersonaRepository extends JpaRepository<Persona, Long> {
    // JpaRepository proporciona métodos CRUD básicos (save, findById, findAll, delete, etc.)

    // Método personalizado para buscar una Persona por su mail (útil para login)
    Optional<Persona> findByMail(String mail);
}