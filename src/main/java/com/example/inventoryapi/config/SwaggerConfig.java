package com.example.inventoryapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * Configuración de Swagger/OpenAPI 3.0
 * 
 * Esta clase configura la documentación automática de la API
 * utilizando OpenAPI 3.0 con soporte para autenticación JWT.
 * 
 * @author Sistema de Inventario
 * @version 1.0.0
 */
@Configuration
public class SwaggerConfig {
    
    /**
     * Configuración principal de OpenAPI
     * 
     * @return configuración de OpenAPI
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(apiInfo())
            .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
            .components(new io.swagger.v3.oas.models.Components()
                .addSecuritySchemes("Bearer Authentication", createAPIKeyScheme()))
            .servers(Arrays.asList(
                new Server().url("http://localhost:8080/api").description("Servidor de Desarrollo"),
                new Server().url("https://inventory-api.example.com/api").description("Servidor de Producción")
            ));
    }
    
    /**
     * Información general de la API
     * 
     * @return información de la API
     */
    private Info apiInfo() {
        return new Info()
            .title("Inventory API")
            .description("API RESTful para gestión de inventario de productos con autenticación JWT")
            .version("1.0.0")
            .contact(new Contact()
                .name("Equipo de Desarrollo")
                .email("desarrollo@inventario.com")
                .url("https://github.com/tu-usuario/inventory-api"))
            .license(new License()
                .name("MIT License")
                .url("https://opensource.org/licenses/MIT"));
    }
    
    /**
     * Esquema de seguridad JWT
     * 
     * @return esquema de seguridad
     */
    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme()
            .type(SecurityScheme.Type.HTTP)
            .bearerFormat("JWT")
            .scheme("bearer")
            .description("Ingresa el token JWT en el formato: Bearer {token}");
    }
}