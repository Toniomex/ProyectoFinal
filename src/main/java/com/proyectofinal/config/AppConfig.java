/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyectofinal.config;

/**
 *
 * @author antoine
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Clase de configuración para beans de aplicación generales.
 * Contiene la definición de BCryptPasswordEncoder para evitar dependencias circulares.
 */
@Configuration
public class AppConfig {

    /**
     * Define el bean BCryptPasswordEncoder para la encriptación de contraseñas.
     * @return Una instancia de BCryptPasswordEncoder.
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}