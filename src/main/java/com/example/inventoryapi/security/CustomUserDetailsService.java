package com.example.inventoryapi.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * Servicio personalizado para cargar detalles de usuario
 * 
 * Esta implementación maneja usuarios en memoria para el sistema de inventario.
 * En un entorno de producción, esto se conectaría a una base de datos.
 * 
 * @author Sistema de Inventario
 * @version 1.0.0
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {
    
    private final PasswordEncoder passwordEncoder;
    
    public CustomUserDetailsService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        // Usuarios predefinidos en memoria
        // En producción, esto se cargaría desde una base de datos
        if ("admin".equals(username)) {
            return User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("password"))
                    .authorities(Arrays.asList(
                            new SimpleGrantedAuthority("ROLE_ADMIN"),
                            new SimpleGrantedAuthority("ROLE_USER")
                    ))
                    .build();
        } else if ("user".equals(username)) {
            return User.builder()
                    .username("user")
                    .password(passwordEncoder.encode("password"))
                    .authorities(Arrays.asList(
                            new SimpleGrantedAuthority("ROLE_USER")
                    ))
                    .build();
        } else {
            throw new UsernameNotFoundException("Usuario no encontrado: " + username);
        }
    }
    
    /**
     * Obtiene la lista de usuarios disponibles
     * 
     * @return lista de nombres de usuario disponibles
     */
    public List<String> getAvailableUsers() {
        return Arrays.asList("admin", "user");
    }
    
    /**
     * Valida las credenciales del usuario
     * 
     * @param username nombre de usuario
     * @param password contraseña
     * @return true si las credenciales son válidas
     */
    public boolean validateUserCredentials(String username, String password) {
        try {
            UserDetails userDetails = loadUserByUsername(username);
            return passwordEncoder.matches(password, userDetails.getPassword());
        } catch (UsernameNotFoundException e) {
            return false;
        }
    }
}