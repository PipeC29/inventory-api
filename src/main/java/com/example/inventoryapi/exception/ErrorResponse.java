package com.example.inventoryapi.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Clase para respuestas de error estructuradas
 * 
 * Esta clase define la estructura estándar para todas las respuestas
 * de error de la API, proporcionando información consistente sobre errores.
 * 
 * @author Sistema de Inventario
 * @version 1.0.0
 */
@Schema(description = "Respuesta de error estándar de la API")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    
    @Schema(description = "Timestamp del error", example = "2024-01-15T10:30:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timestamp;
    
    @Schema(description = "Código de estado HTTP", example = "404")
    private int status;
    
    @Schema(description = "Código de error interno", example = "PRODUCT_NOT_FOUND")
    private String error;
    
    @Schema(description = "Mensaje descriptivo del error", example = "Producto no encontrado con ID: 123")
    private String message;
    
    @Schema(description = "Ruta donde ocurrió el error", example = "/api/products/123")
    private String path;
    
    @Schema(description = "Errores de validación específicos por campo")
    private Map<String, String> validationErrors;
    
    public ErrorResponse() {
    }
    
    public ErrorResponse(LocalDateTime timestamp, int status, String error, String message, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }
    
    public ErrorResponse(LocalDateTime timestamp, int status, String error, String message, 
                        String path, Map<String, String> validationErrors) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
        this.validationErrors = validationErrors;
    }
    
    // Getters y Setters
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    
    public int getStatus() {
        return status;
    }
    
    public void setStatus(int status) {
        this.status = status;
    }
    
    public String getError() {
        return error;
    }
    
    public void setError(String error) {
        this.error = error;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public String getPath() {
        return path;
    }
    
    public void setPath(String path) {
        this.path = path;
    }
    
    public Map<String, String> getValidationErrors() {
        return validationErrors;
    }
    
    public void setValidationErrors(Map<String, String> validationErrors) {
        this.validationErrors = validationErrors;
    }
    
    @Override
    public String toString() {
        return "ErrorResponse{" +
                "timestamp=" + timestamp +
                ", status=" + status +
                ", error='" + error + '\'' +
                ", message='" + message + '\'' +
                ", path='" + path + '\'' +
                ", validationErrors=" + validationErrors +
                '}';
    }
}