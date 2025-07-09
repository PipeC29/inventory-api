package com.example.inventoryapi.exception;

/**
 * Excepción personalizada para cuando no se encuentra un producto
 * 
 * Esta excepción se lanza cuando se intenta acceder a un producto
 * que no existe en la base de datos.
 * 
 * @author Sistema de Inventario
 * @version 1.0.0
 */
public class ProductNotFoundException extends RuntimeException {
    
    /**
     * Constructor con mensaje de error
     * 
     * @param message mensaje descriptivo del error
     */
    public ProductNotFoundException(String message) {
        super(message);
    }
    
    /**
     * Constructor con mensaje de error y causa
     * 
     * @param message mensaje descriptivo del error
     * @param cause causa raíz de la excepción
     */
    public ProductNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}