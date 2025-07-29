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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter.Directive;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * Clase de configuración de seguridad para la aplicación Spring Boot.
 * Esta configuración permite el acceso a los recursos estáticos y a la API REST
 * sin autenticación para fines de desarrollo y demostración.
 * También configura CORS para permitir solicitudes desde el frontend.
 */
@Configuration
@EnableWebSecurity // Habilita la configuración de seguridad web de Spring Security
public class SecurityConfig {

    /**
     * Define la cadena de filtros de seguridad.
     *
     * @param http Objeto HttpSecurity para configurar la seguridad.
     * @return La cadena de filtros de seguridad configurada.
     * @throws Exception Si ocurre un error durante la configuración.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Deshabilita CSRF (Cross-Site Request Forgery) para permitir solicitudes POST desde el frontend
            // En una aplicación de producción con un frontend separado, CSRF debe manejarse adecuadamente.
            .csrf(csrf -> csrf.disable())
            // Configura las reglas de autorización para las solicitudes HTTP
            .authorizeHttpRequests(authorize -> authorize
                // Permite el acceso a todos los archivos estáticos (HTML, CSS, JS, imágenes)
                .requestMatchers("/", "/index.html", "/css/**", "/js/**", "/favicon.ico").permitAll()
                // Permite el acceso a todas las rutas de la API (ej. /api/personas/1, /api/contratos/inquilino/1)
                // Esto es crucial para que el frontend pueda comunicarse con el backend sin autenticación por ahora.
                .requestMatchers("/api/**").permitAll()
                // **CAMBIO AQUÍ**: Permite el acceso a CUALQUIER otra solicitud.
                // Esto asegura que no haya rutas bloqueadas por defecto en esta demo.
                .anyRequest().permitAll()
            )
            // Deshabilita la autenticación por formulario (formLogin)
            .formLogin(formLogin -> formLogin.disable())
            // Deshabilita la autenticación HTTP básica (httpBasic)
            .httpBasic(httpBasic -> httpBasic.disable())
            // Configura CORS (Cross-Origin Resource Sharing) para permitir solicitudes desde el dominio del frontend
            // Esto es necesario si tu frontend se ejecuta en un dominio/puerto diferente al backend (ej. 3000 vs 8080)
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            // Configura cabeceras para limpiar los datos del sitio al cerrar la sesión (útil en producción)
            .headers(headers -> headers
                .addHeaderWriter(new ClearSiteDataHeaderWriter(Directive.ALL))
            );
        return http.build();
    }

    /**
     * Configura las políticas de CORS (Cross-Origin Resource Sharing).
     * Permite que el frontend (ej. localhost:3000 o localhost:8080 si se abre el HTML directamente)
     * pueda realizar solicitudes al backend.
     *
     * @return Una fuente de configuración CORS basada en URL.
     */
    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Permite solicitudes desde cualquier origen. En producción, deberías especificar dominios exactos.
        configuration.addAllowedOrigin("*");
        // Permite todos los métodos HTTP (GET, POST, PUT, DELETE, etc.)
        configuration.addAllowedMethod("*");
        // Permite todas las cabeceras HTTP
        configuration.addAllowedHeader("*");
        // Permite el envío de credenciales (cookies, encabezados de autorización)
        configuration.setAllowCredentials(false); // Cambiado a false ya que no estamos usando cookies/sesiones con credenciales explícitas
                                                 // Si se usara JWT con encabezados de autorización, esto podría ser true.
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // Aplica esta configuración CORS a todas las rutas de la API
        source.registerCorsConfiguration("/api/**", configuration);
        return source;
    }

    /**
     * Define el bean PasswordEncoder para el cifrado de contraseñas.
     * Usamos BCryptPasswordEncoder, que es un algoritmo de hashing seguro.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Define el bean AuthenticationManager.
     * Es responsable de la autenticación de usuarios.
     * Requiere un UserDetailsService para cargar los detalles del usuario
     * y un PasswordEncoder para verificar las contraseñas.
     *
     * @param userDetailsService El servicio para cargar los detalles del usuario.
     * @param passwordEncoder El codificador de contraseñas.
     * @return El AuthenticationManager configurado.
     */
    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(authenticationProvider);
    }
}