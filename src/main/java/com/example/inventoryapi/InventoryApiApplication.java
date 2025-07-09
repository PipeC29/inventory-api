package com.example.inventoryapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal de la aplicación Inventory API
 * 
 * Esta aplicación proporciona una API RESTful completa para gestionar 
 * un inventario de productos con autenticación JWT, documentación Swagger
 * y base de datos H2 en memoria.
 * 
 * @author Sistema de Inventario
 * @version 1.0.0
 */
@SpringBootApplication
public class InventoryApiApplication {

    /**
     * Método principal que inicia la aplicación Spring Boot
     * 
     * @param args argumentos de línea de comandos
     */
    public static void main(String[] args) {
        SpringApplication.run(InventoryApiApplication.class, args);
    }
}