package com.example.inventoryapi.controller;

import com.example.inventoryapi.dto.ProductRequestDto;
import com.example.inventoryapi.dto.ProductResponseDto;
import com.example.inventoryapi.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Controlador REST para operaciones de productos
 * 
 * Este controlador proporciona endpoints para todas las operaciones CRUD
 * de productos, incluyendo búsquedas y consultas especializadas.
 * 
 * @author Sistema de Inventario
 * @version 1.0.0
 */
@RestController
@RequestMapping("/products")
@Tag(name = "Productos", description = "API para gestión de productos del inventario")
@SecurityRequirement(name = "Bearer Authentication")
public class ProductController {
    
    private final ProductService productService;
    
    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    
    /**
     * Obtiene todos los productos
     */
    @GetMapping
    @Operation(summary = "Obtener todos los productos", description = "Recupera una lista de todos los productos en el inventario")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de productos obtenida exitosamente"),
        @ApiResponse(responseCode = "401", description = "No autorizado - Token JWT requerido"),
        @ApiResponse(responseCode = "403", description = "Acceso denegado")
    })
    public ResponseEntity<List<ProductResponseDto>> getAllProducts() {
        List<ProductResponseDto> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }
    
    /**
     * Obtiene un producto por su ID
     */
    @GetMapping("/{id}")
    @Operation(summary = "Obtener producto por ID", description = "Recupera un producto específico por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto encontrado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado"),
        @ApiResponse(responseCode = "401", description = "No autorizado - Token JWT requerido")
    })
    public ResponseEntity<ProductResponseDto> getProductById(
            @Parameter(description = "ID del producto a obtener", example = "1")
            @PathVariable Long id) {
        ProductResponseDto product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }
    
    /**
     * Crea un nuevo producto
     */
    @PostMapping
    @Operation(summary = "Crear nuevo producto", description = "Crea un nuevo producto en el inventario")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Producto creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "401", description = "No autorizado - Token JWT requerido")
    })
    public ResponseEntity<ProductResponseDto> createProduct(
            @Parameter(description = "Datos del producto a crear")
            @Valid @RequestBody ProductRequestDto productRequest) {
        ProductResponseDto createdProduct = productService.createProduct(productRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }
    
    /**
     * Actualiza un producto existente
     */
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar producto", description = "Actualiza un producto existente por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto actualizado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "401", description = "No autorizado - Token JWT requerido")
    })
    public ResponseEntity<ProductResponseDto> updateProduct(
            @Parameter(description = "ID del producto a actualizar", example = "1")
            @PathVariable Long id,
            @Parameter(description = "Nuevos datos del producto")
            @Valid @RequestBody ProductRequestDto productRequest) {
        ProductResponseDto updatedProduct = productService.updateProduct(id, productRequest);
        return ResponseEntity.ok(updatedProduct);
    }
    
    /**
     * Elimina un producto
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar producto", description = "Elimina un producto del inventario por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Producto eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado"),
        @ApiResponse(responseCode = "401", description = "No autorizado - Token JWT requerido")
    })
    public ResponseEntity<Void> deleteProduct(
            @Parameter(description = "ID del producto a eliminar", example = "1")
            @PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
    
    /**
     * Busca productos por nombre
     */
    @GetMapping("/search")
    @Operation(summary = "Buscar productos por nombre", description = "Busca productos que contengan el texto especificado en el nombre")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Búsqueda completada exitosamente"),
        @ApiResponse(responseCode = "401", description = "No autorizado - Token JWT requerido")
    })
    public ResponseEntity<List<ProductResponseDto>> searchProductsByName(
            @Parameter(description = "Texto a buscar en el nombre del producto", example = "laptop")
            @RequestParam String name) {
        List<ProductResponseDto> products = productService.searchProductsByName(name);
        return ResponseEntity.ok(products);
    }
    
    /**
     * Obtiene productos por rango de precios
     */
    @GetMapping("/price-range")
    @Operation(summary = "Obtener productos por rango de precios", description = "Recupera productos dentro de un rango de precios específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Productos obtenidos exitosamente"),
        @ApiResponse(responseCode = "400", description = "Rango de precios inválido"),
        @ApiResponse(responseCode = "401", description = "No autorizado - Token JWT requerido")
    })
    public ResponseEntity<List<ProductResponseDto>> getProductsByPriceRange(
            @Parameter(description = "Precio mínimo", example = "100.00")
            @RequestParam BigDecimal minPrice,
            @Parameter(description = "Precio máximo", example = "500.00")
            @RequestParam BigDecimal maxPrice) {
        List<ProductResponseDto> products = productService.getProductsByPriceRange(minPrice, maxPrice);
        return ResponseEntity.ok(products);
    }
    
    /**
     * Obtiene productos con stock bajo
     */
    @GetMapping("/low-stock")
    @Operation(summary = "Obtener productos con stock bajo", description = "Recupera productos con cantidad menor o igual al umbral especificado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Productos con stock bajo obtenidos exitosamente"),
        @ApiResponse(responseCode = "401", description = "No autorizado - Token JWT requerido")
    })
    public ResponseEntity<List<ProductResponseDto>> getLowStockProducts(
            @Parameter(description = "Umbral de stock bajo", example = "10")
            @RequestParam(defaultValue = "10") Integer threshold) {
        List<ProductResponseDto> products = productService.getLowStockProducts(threshold);
        return ResponseEntity.ok(products);
    }
    
    /**
     * Obtiene productos con stock disponible
     */
    @GetMapping("/in-stock")
    @Operation(summary = "Obtener productos en stock", description = "Recupera productos con cantidad disponible mayor a 0")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Productos en stock obtenidos exitosamente"),
        @ApiResponse(responseCode = "401", description = "No autorizado - Token JWT requerido")
    })
    public ResponseEntity<List<ProductResponseDto>> getProductsInStock() {
        List<ProductResponseDto> products = productService.getProductsInStock();
        return ResponseEntity.ok(products);
    }
    
    /**
     * Obtiene estadísticas del inventario
     */
    @GetMapping("/stats")
    @Operation(summary = "Obtener estadísticas del inventario", description = "Recupera estadísticas generales del inventario")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Estadísticas obtenidas exitosamente"),
        @ApiResponse(responseCode = "401", description = "No autorizado - Token JWT requerido")
    })
    public ResponseEntity<Map<String, Object>> getInventoryStats() {
        long totalProducts = productService.getTotalProductCount();
        BigDecimal totalValue = productService.calculateTotalInventoryValue();
        
        Map<String, Object> stats = Map.of(
            "totalProducts", totalProducts,
            "totalInventoryValue", totalValue,
            "averageProductValue", totalProducts > 0 ? totalValue.divide(BigDecimal.valueOf(totalProducts), 2, BigDecimal.ROUND_HALF_UP) : BigDecimal.ZERO
        );
        
        return ResponseEntity.ok(stats);
    }
}