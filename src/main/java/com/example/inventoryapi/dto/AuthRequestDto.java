package com.example.inventoryapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO para las peticiones de autenticación
 * 
 * Este DTO define la estructura de datos que se recibe en las peticiones
 * de autenticación JWT.
 * 
 * @author Sistema de Inventario
 * @version 1.0.0
 */
@Schema(description = "Credenciales de autenticación")
public class AuthRequestDto {
    
    @Schema(description = "Nombre de usuario", example = "admin")
    @NotBlank(message = "El nombre de usuario es obligatorio")
    private String username;
    
    @Schema(description = "Contraseña", example = "password")
    @NotBlank(message = "La contraseña es obligatoria")
    private String password;
    
    public AuthRequestDto() {
    }
    
    public AuthRequestDto(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
    // Getters y Setters
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    @Override
    public String toString() {
        return "AuthRequestDto{" +
                "username='" + username + '\'' +
                ", password='[PROTECTED]'" +
                '}';
    }
}