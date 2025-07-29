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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken; // Asegurarse de que esté importado
import org.springframework.security.authentication.dao.DaoAuthenticationProvider; // Asegurarse de que esté importado
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
// No se necesita importar UsernamePasswordAuthenticationFilter si no se usa directamente en el filtro de cadena

/**
 * Clase de configuración de Spring Security.
 * Define las reglas de seguridad, el codificador de contraseñas y el servicio de detalles de usuario.
 */
@Configuration
@EnableWebSecurity // Habilita la seguridad web de Spring
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    // Ya no se define el bean BCryptPasswordEncoder aquí, se obtiene de AppConfig.java
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
                .requestMatchers("/", "/index.html", "/api/register", "/api/login", "/css/**", "/js/**", "/error").permitAll()
                // Rutas protegidas que requieren autenticación
                .requestMatchers("/api/**").authenticated() // Todas las demás rutas /api requieren autenticación
                // Puedes añadir más reglas específicas para roles aquí, ej:
                // .requestMatchers("/admin/**").hasRole("ADMIN")
                // .requestMatchers("/user/**").hasRole("INQUILINO")
                .anyRequest().authenticated() // Cualquier otra solicitud requiere autenticación
            )
            .formLogin(formLogin -> formLogin
                .loginPage("/index.html") // Página de login personalizada (la misma que la principal)
                .loginProcessingUrl("/api/login") // URL a la que se envía el formulario de login (POST)
                .defaultSuccessUrl("/index.html", true) // Redirección tras login exitoso
                .failureUrl("/index.html?error=true") // Redirección tras login fallido
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/api/logout") // URL para cerrar sesión
                .logoutSuccessUrl("/index.html?logout=true") // Redirección tras logout exitoso
                .permitAll()
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // Usa sesión si es necesario (por defecto)
            );
            // Si necesitas autenticación basada en JWT o algo más complejo, aquí irían los filtros adicionales.

        return http.build();
    }

    /**
     * Define el AuthenticationManager.
     * @param http HttpSecurity para construir el AuthenticationManager.
     * @return El AuthenticationManager configurado.
     * @throws Exception Si ocurre un error.
     */
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder) throws Exception {
        // Inyectamos BCryptPasswordEncoder directamente como parámetro, Spring lo encontrará
        AuthenticationManagerBuilder authenticationManagerBuilder =
            http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
            .userDetailsService(customUserDetailsService)
            .passwordEncoder(bCryptPasswordEncoder); // Usar el encoder inyectado
        return authenticationManagerBuilder.build();
    }
}