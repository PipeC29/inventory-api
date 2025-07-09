package com.example.inventoryapi.service;

import com.example.inventoryapi.dto.ProductRequestDto;
import com.example.inventoryapi.dto.ProductResponseDto;
import java.math.BigDecimal;
import java.util.List;

/**
 * Interfaz del servicio para operaciones de productos
 * 
 * Esta interfaz define los métodos de negocio para la gestión de productos,
 * siguiendo el principio de inversión de dependencias.
 * 
 * @author Sistema de Inventario
 * @version 1.0.0
 */
public interface ProductService {
    
    /**
     * Obtiene todos los productos
     * 
     * @return lista de todos los productos
     */
    List<ProductResponseDto> getAllProducts();
    
    /**
     * Obtiene un producto por su ID
     * 
     * @param id ID del producto
     * @return producto encontrado
     * @throws RuntimeException si el producto no existe
     */
    ProductResponseDto getProductById(Long id);
    
    /**
     * Crea un nuevo producto
     * 
     * @param productRequest datos del producto a crear
     * @return producto creado
     */
    ProductResponseDto createProduct(ProductRequestDto productRequest);
    
    /**
     * Actualiza un producto existente
     * 
     * @param id ID del producto a actualizar
     * @param productRequest nuevos datos del producto
     * @return producto actualizado
     * @throws RuntimeException si el producto no existe
     */
    ProductResponseDto updateProduct(Long id, ProductRequestDto productRequest);
    
    /**
     * Elimina un producto por su ID
     * 
     * @param id ID del producto a eliminar
     * @throws RuntimeException si el producto no existe
     */
    void deleteProduct(Long id);
    
    /**
     * Busca productos por nombre (búsqueda parcial)
     * 
     * @param name nombre o parte del nombre a buscar
     * @return lista de productos que coinciden
     */
    List<ProductResponseDto> searchProductsByName(String name);
    
    /**
     * Busca productos por rango de precios
     * 
     * @param minPrice precio mínimo
     * @param maxPrice precio máximo
     * @return lista de productos dentro del rango
     */
    List<ProductResponseDto> getProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice);
    
    /**
     * Obtiene productos con stock bajo
     * 
     * @param threshold umbral de stock bajo
     * @return lista de productos con stock bajo
     */
    List<ProductResponseDto> getLowStockProducts(Integer threshold);
    
    /**
     * Obtiene productos con stock disponible
     * 
     * @return lista de productos con stock > 0
     */
    List<ProductResponseDto> getProductsInStock();
    
    /**
     * Calcula el valor total del inventario
     * 
     * @return valor total del inventario
     */
    BigDecimal calculateTotalInventoryValue();
    
    /**
     * Obtiene el número total de productos
     * 
     * @return número total de productos
     */
    long getTotalProductCount();
}