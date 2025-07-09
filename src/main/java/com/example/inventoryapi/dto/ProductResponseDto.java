package com.example.inventoryapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO para las respuestas de productos
 * 
 * Este DTO define la estructura de datos que se devuelve en las respuestas
 * HTTP cuando se consultan productos.
 * 
 * @author Sistema de Inventario
 * @version 1.0.0
 */
@Schema(description = "Información completa de un producto")
public class ProductResponseDto {
    
    @Schema(description = "ID único del producto", example = "1")
    private Long id;
    
    @Schema(description = "Nombre del producto", example = "Laptop Dell XPS 13")
    private String name;
    
    @Schema(description = "Descripción del producto", example = "Laptop ultrabook con procesador Intel i7")
    private String description;
    
    @Schema(description = "Precio del producto", example = "999.99")
    private BigDecimal price;
    
    @Schema(description = "Cantidad disponible en inventario", example = "50")
    private Integer quantity;
    
    @Schema(description = "Fecha de creación del producto", example = "2024-01-15T10:30:00")
    private LocalDateTime createdAt;
    
    @Schema(description = "Fecha de última actualización", example = "2024-01-15T10:30:00")
    private LocalDateTime updatedAt;
    
    public ProductResponseDto() {
    }
    
    public ProductResponseDto(Long id, String name, String description, BigDecimal price, 
                            Integer quantity, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    
    // Getters y Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
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
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    @Override
    public String toString() {
        return "ProductResponseDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}