package com.example.inventoryapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO para las respuestas de autenticación
 * 
 * Este DTO define la estructura de datos que se devuelve tras
 * una autenticación exitosa.
 * 
 * @author Sistema de Inventario
 * @version 1.0.0
 */
@Schema(description = "Respuesta de autenticación exitosa")
public class AuthResponseDto {
    
    @Schema(description = "Token JWT de acceso", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String token;
    
    @Schema(description = "Tipo de token", example = "Bearer")
    private String type = "Bearer";
    
    @Schema(description = "Nombre de usuario autenticado", example = "admin")
    private String username;
    
    @Schema(description = "Tiempo de expiración del token en milisegundos", example = "86400000")
    private Long expiresIn;
    
    public AuthResponseDto() {
    }
    
    public AuthResponseDto(String token, String username, Long expiresIn) {
        this.token = token;
        this.username = username;
        this.expiresIn = expiresIn;
    }
    
    // Getters y Setters
    public String getToken() {
        return token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public Long getExpiresIn() {
        return expiresIn;
    }
    
    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }
    
    @Override
    public String toString() {
        return "AuthResponseDto{" +
                "token='[PROTECTED]'" +
                ", type='" + type + '\'' +
                ", username='" + username + '\'' +
                ", expiresIn=" + expiresIn +
                '}';
    }
}