package com.example.inventoryapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Manejador global de excepciones
 * 
 * Esta clase maneja todas las excepciones que ocurren en la aplicación
 * y proporciona respuestas consistentes para los errores.
 * 
 * @author Sistema de Inventario
 * @version 1.0.0
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    
    /**
     * Maneja excepciones de producto no encontrado
     * 
     * @param ex excepción ProductNotFoundException
     * @param request contexto de la petición
     * @return respuesta de error estructurada
     */
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProductNotFoundException(
            ProductNotFoundException ex, WebRequest request) {
        
        ErrorResponse errorResponse = new ErrorResponse(
            LocalDateTime.now(),
            HttpStatus.NOT_FOUND.value(),
            "NOT_FOUND",
            ex.getMessage(),
            request.getDescription(false)
        );
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
    
    /**
     * Maneja excepciones de validación
     * 
     * @param ex excepción MethodArgumentNotValidException
     * @param request contexto de la petición
     * @return respuesta de error con detalles de validación
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(
            MethodArgumentNotValidException ex, WebRequest request) {
        
        Map<String, String> validationErrors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            validationErrors.put(fieldName, errorMessage);
        });
        
        ErrorResponse errorResponse = new ErrorResponse(
            LocalDateTime.now(),
            HttpStatus.BAD_REQUEST.value(),
            "VALIDATION_ERROR",
            "Error de validación en los datos de entrada",
            request.getDescription(false)
        );
        errorResponse.setValidationErrors(validationErrors);
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
    
    /**
     * Maneja excepciones de argumentos ilegales
     * 
     * @param ex excepción IllegalArgumentException
     * @param request contexto de la petición
     * @return respuesta de error estructurada
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(
            IllegalArgumentException ex, WebRequest request) {
        
        ErrorResponse errorResponse = new ErrorResponse(
            LocalDateTime.now(),
            HttpStatus.BAD_REQUEST.value(),
            "BAD_REQUEST",
            ex.getMessage(),
            request.getDescription(false)
        );
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
    
    /**
     * Maneja excepciones de acceso denegado
     * 
     * @param ex excepción Exception
     * @param request contexto de la petición
     * @return respuesta de error estructurada
     */
    @ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(
            org.springframework.security.access.AccessDeniedException ex, WebRequest request) {
        
        ErrorResponse errorResponse = new ErrorResponse(
            LocalDateTime.now(),
            HttpStatus.FORBIDDEN.value(),
            "ACCESS_DENIED",
            "Acceso denegado: " + ex.getMessage(),
            request.getDescription(false)
        );
        
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }
    
    /**
     * Maneja excepciones de violación de integridad de datos
     * 
     * @param ex excepción DataIntegrityViolationException
     * @param request contexto de la petición
     * @return respuesta de error estructurada
     */
    @ExceptionHandler(org.springframework.dao.DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(
            org.springframework.dao.DataIntegrityViolationException ex, WebRequest request) {
        
        ErrorResponse errorResponse = new ErrorResponse(
            LocalDateTime.now(),
            HttpStatus.CONFLICT.value(),
            "DATA_INTEGRITY_ERROR",
            "Error de integridad de datos: Ya existe un producto con ese nombre",
            request.getDescription(false)
        );
        
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }
    
    /**
     * Maneja excepciones de constraint de validación
     * 
     * @param ex excepción ConstraintViolationException
     * @param request contexto de la petición
     * @return respuesta de error estructurada
     */
    @ExceptionHandler(jakarta.validation.ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(
            jakarta.validation.ConstraintViolationException ex, WebRequest request) {
        
        Map<String, String> validationErrors = new HashMap<>();
        ex.getConstraintViolations().forEach(violation -> {
            String fieldName = violation.getPropertyPath().toString();
            String errorMessage = violation.getMessage();
            validationErrors.put(fieldName, errorMessage);
        });
        
        ErrorResponse errorResponse = new ErrorResponse(
            LocalDateTime.now(),
            HttpStatus.BAD_REQUEST.value(),
            "CONSTRAINT_VIOLATION",
            "Error de validación de constraints",
            request.getDescription(false)
        );
        errorResponse.setValidationErrors(validationErrors);
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
    
    /**
     * Maneja todas las demás excepciones no específicas
     * 
     * @param ex excepción Exception
     * @param request contexto de la petición
     * @return respuesta de error estructurada
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(
            Exception ex, WebRequest request) {
        
        ErrorResponse errorResponse = new ErrorResponse(
            LocalDateTime.now(),
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "INTERNAL_SERVER_ERROR",
            "Error interno del servidor: " + ex.getMessage(),
            request.getDescription(false)
        );
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
    
    /**
     * Maneja excepciones de runtime
     * 
     * @param ex excepción RuntimeException
     * @param request contexto de la petición
     * @return respuesta de error estructurada
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(
            RuntimeException ex, WebRequest request) {
        
        ErrorResponse errorResponse = new ErrorResponse(
            LocalDateTime.now(),
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "RUNTIME_ERROR",
            "Error en tiempo de ejecución: " + ex.getMessage(),
            request.getDescription(false)
        );
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}