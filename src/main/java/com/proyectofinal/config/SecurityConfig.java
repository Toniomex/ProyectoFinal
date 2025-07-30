/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyectofinal.config;
/**
 *
 * @author antoine
 */
import com.proyectofinal.service.impl.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Clase de configuración de Spring Security.
 * Define las reglas de seguridad, el codificador de contraseñas y el servicio de detalles de usuario.
 */
@Configuration
@EnableWebSecurity // Habilita la seguridad web de Spring
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    // El bean BCryptPasswordEncoder se define en AppConfig.java para evitar duplicidades.
    // @Bean
    // public BCryptPasswordEncoder bCryptPasswordEncoder() {
    //     return new BCryptPasswordEncoder();
    // }

    /**
     * Configura la cadena de filtros de seguridad HTTP.
     * @param http El objeto HttpSecurity para configurar.
     * @return La cadena de filtros de seguridad configurada.
     * @throws Exception Si ocurre un error durante la configuración.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Deshabilita CSRF para APIs REST (considerar implicaciones de seguridad)
            .authorizeHttpRequests(authorize -> authorize
                // Rutas públicas que no requieren autenticación
                // Permitir acceso a la raíz, index.html, y los endpoints de registro/login/logout
                .requestMatchers("/", "/index.html", "/api/register", "/api/login", "/api/logout", "/css/**", "/js/**", "/error").permitAll()
                // Todas las demás rutas /api requieren autenticación
                .requestMatchers("/api/**").authenticated()
                // Cualquier otra solicitud no especificada requiere autenticación
                .anyRequest().authenticated()
            )
            // ELIMINADAS LAS CONFIGURACIONES DE formLogin y logout por defecto.
            // Tu PersonaController ahora maneja /api/login y /api/logout directamente.
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // Usa sesión si es necesario (por defecto)
            );

        return http.build();
    }

    /**
     * Define el AuthenticationManager.
     * @param http HttpSecurity para construir el AuthenticationManager.
     * @param bCryptPasswordEncoder El codificador de contraseñas inyectado desde AppConfig.
     * @return El AuthenticationManager configurado.
     * @throws Exception Si ocurre un error.
     */
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
            http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
            .userDetailsService(customUserDetailsService)
            .passwordEncoder(bCryptPasswordEncoder);
        return authenticationManagerBuilder.build();
    }
}