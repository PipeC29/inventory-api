package com.example.inventoryapi.config;

import com.example.inventoryapi.security.CustomUserDetailsService;
import com.example.inventoryapi.security.JwtAuthenticationEntryPoint;
import com.example.inventoryapi.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuración de seguridad de Spring Security
 * 
 * Esta clase configura la seguridad de la aplicación, incluyendo
 * autenticación JWT, autorización y filtros de seguridad.
 * 
 * @author Sistema de Inventario
 * @version 1.0.0
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    
    @Autowired
    private CustomUserDetailsService userDetailsService;
    
    /**
     * Configura el encoder de contraseñas
     * 
     * @return encoder BCrypt
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    /**
     * Configura el proveedor de autenticación
     * 
     * @return proveedor de autenticación DAO
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
    
    /**
     * Configura el manager de autenticación
     * 
     * @param config configuración de autenticación
     * @return manager de autenticación
     * @throws Exception en caso de error
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
    
    /**
     * Configura la cadena de filtros de seguridad
     * 
     * @param http configuración HTTP
     * @return cadena de filtros de seguridad
     * @throws Exception en caso de error
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(authz -> authz
                // Permitir acceso público a endpoints de autenticación
                .requestMatchers("/auth/**").permitAll()
                // Permitir acceso a Swagger UI y documentación
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()
                // Permitir acceso a la consola H2
                .requestMatchers("/h2-console/**").permitAll()
                // Permitir acceso a actuator endpoints (opcional)
                .requestMatchers("/actuator/**").permitAll()
                // Todos los demás endpoints requieren autenticación
                .anyRequest().authenticated()
            )
            .exceptionHandling(ex -> ex.authenticationEntryPoint(jwtAuthenticationEntryPoint))
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        
        // Configuración específica para H2 Console
        http.headers(headers -> headers.frameOptions().sameOrigin());
        
        // Agregar filtro JWT antes del filtro de autenticación por defecto
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }
}