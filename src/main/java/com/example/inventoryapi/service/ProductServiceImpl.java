package com.example.inventoryapi.service;

import com.example.inventoryapi.dto.ProductRequestDto;
import com.example.inventoryapi.dto.ProductResponseDto;
import com.example.inventoryapi.exception.ProductNotFoundException;
import com.example.inventoryapi.model.Product;
import com.example.inventoryapi.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación del servicio para operaciones de productos
 * 
 * Esta clase implementa la lógica de negocio para la gestión de productos,
 * incluyendo validaciones y transformaciones de datos.
 * 
 * @author Sistema de Inventario
 * @version 1.0.0
 */
@Service
@Transactional
public class ProductServiceImpl implements ProductService {
    
    private final ProductRepository productRepository;
    
    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public ProductResponseDto getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Producto no encontrado con ID: " + id));
        return convertToResponseDto(product);
    }
    
    @Override
    public ProductResponseDto createProduct(ProductRequestDto productRequest) {
        validateProductRequest(productRequest);
        
        Product product = new Product();
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setQuantity(productRequest.getQuantity());
        
        Product savedProduct = productRepository.save(product);
        return convertToResponseDto(savedProduct);
    }
    
    @Override
    public ProductResponseDto updateProduct(Long id, ProductRequestDto productRequest) {
        validateProductRequest(productRequest);
        
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Producto no encontrado con ID: " + id));
        
        existingProduct.setName(productRequest.getName());
        existingProduct.setDescription(productRequest.getDescription());
        existingProduct.setPrice(productRequest.getPrice());
        existingProduct.setQuantity(productRequest.getQuantity());
        
        Product updatedProduct = productRepository.save(existingProduct);
        return convertToResponseDto(updatedProduct);
    }
    
    @Override
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("Producto no encontrado con ID: " + id);
        }
        productRepository.deleteById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDto> searchProductsByName(String name) {
        List<Product> products = productRepository.findByNameContainingIgnoreCase(name);
        return products.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDto> getProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        if (minPrice.compareTo(maxPrice) > 0) {
            throw new IllegalArgumentException("El precio mínimo no puede ser mayor al precio máximo");
        }
        
        List<Product> products = productRepository.findByPriceBetween(minPrice, maxPrice);
        return products.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDto> getLowStockProducts(Integer threshold) {
        if (threshold < 0) {
            throw new IllegalArgumentException("El umbral de stock no puede ser negativo");
        }
        
        List<Product> products = productRepository.findByQuantityLessThanEqual(threshold);
        return products.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDto> getProductsInStock() {
        List<Product> products = productRepository.findProductsInStock();
        return products.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public BigDecimal calculateTotalInventoryValue() {
        BigDecimal totalValue = productRepository.calculateTotalInventoryValue();
        return totalValue != null ? totalValue : BigDecimal.ZERO;
    }
    
    @Override
    @Transactional(readOnly = true)
    public long getTotalProductCount() {
        return productRepository.countTotalProducts();
    }
    
    /**
     * Convierte una entidad Product a ProductResponseDto
     * 
     * @param product entidad Product
     * @return ProductResponseDto
     */
    private ProductResponseDto convertToResponseDto(Product product) {
        return new ProductResponseDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getQuantity(),
                product.getCreatedAt(),
                product.getUpdatedAt()
        );
    }
    
    /**
     * Valida los datos de un ProductRequestDto
     * 
     * @param productRequest datos del producto a validar
     * @throws IllegalArgumentException si hay errores de validación
     */
    private void validateProductRequest(ProductRequestDto productRequest) {
        if (productRequest == null) {
            throw new IllegalArgumentException("Los datos del producto no pueden ser nulos");
        }
        
        if (productRequest.getName() == null || productRequest.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del producto es obligatorio");
        }
        
        if (productRequest.getPrice() == null || productRequest.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor a cero");
        }
        
        if (productRequest.getQuantity() == null || productRequest.getQuantity() < 0) {
            throw new IllegalArgumentException("La cantidad no puede ser negativa");
        }
    }
}