package com.example.inventoryapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

/**
 * DTO para las peticiones de creación y actualización de productos
 * 
 * Este DTO define la estructura de datos que se recibe en las peticiones
 * HTTP para crear o actualizar productos, incluyendo validaciones.
 * 
 * @author Sistema de Inventario
 * @version 1.0.0
 */
@Schema(description = "Datos de entrada para crear o actualizar un producto")
public class ProductRequestDto {
    
    @Schema(description = "Nombre del producto", example = "Laptop Dell XPS 13")
    @NotBlank(message = "El nombre del producto es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String name;
    
    @Schema(description = "Descripción del producto", example = "Laptop ultrabook con procesador Intel i7")
    @Size(max = 500, message = "La descripción no puede exceder 500 caracteres")
    private String description;
    
    @Schema(description = "Precio del producto", example = "999.99")
    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a 0")
    @Digits(integer = 10, fraction = 2, message = "El precio debe tener máximo 10 dígitos enteros y 2 decimales")
    private BigDecimal price;
    
    @Schema(description = "Cantidad disponible en inventario", example = "50")
    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 0, message = "La cantidad no puede ser negativa")
    private Integer quantity;
    
    public ProductRequestDto() {
    }
    
    public ProductRequestDto(String name, String description, BigDecimal price, Integer quantity) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }
    
    // Getters y Setters
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public BigDecimal getPrice() {
        return price;
    }
    
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    
    public Integer getQuantity() {
        return quantity;
    }
    
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    
    @Override
    public String toString() {
        return "ProductRequestDto{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}