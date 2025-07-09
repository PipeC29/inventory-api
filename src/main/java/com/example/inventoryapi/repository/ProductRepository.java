package com.example.inventoryapi.repository;

import com.example.inventoryapi.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la entidad Product
 * 
 * Esta interfaz extiende JpaRepository para proporcionar operaciones CRUD
 * básicas y métodos de consulta personalizados para productos.
 * 
 * @author Sistema de Inventario
 * @version 1.0.0
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    /**
     * Busca productos por nombre (búsqueda parcial, insensible a mayúsculas)
     * 
     * @param name parte del nombre a buscar
     * @return lista de productos que coinciden con el nombre
     */
    List<Product> findByNameContainingIgnoreCase(String name);
    
    /**
     * Busca productos por rango de precios
     * 
     * @param minPrice precio mínimo
     * @param maxPrice precio máximo
     * @return lista de productos dentro del rango de precios
     */
    List<Product> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);
    
    /**
     * Busca productos con cantidad menor o igual a la especificada
     * 
     * @param quantity cantidad límite
     * @return lista de productos con stock bajo
     */
    List<Product> findByQuantityLessThanEqual(Integer quantity);
    
    /**
     * Verifica si existe un producto con el nombre especificado
     * 
     * @param name nombre del producto
     * @return true si existe, false en caso contrario
     */
    boolean existsByNameIgnoreCase(String name);
    
    /**
     * Busca un producto por nombre exacto (insensible a mayúsculas)
     * 
     * @param name nombre del producto
     * @return Optional con el producto si se encuentra
     */
    Optional<Product> findByNameIgnoreCase(String name);
    
    /**
     * Cuenta el número total de productos en inventario
     * 
     * @return número total de productos
     */
    @Query("SELECT COUNT(p) FROM Product p")
    long countTotalProducts();
    
    /**
     * Calcula el valor total del inventario
     * 
     * @return valor total del inventario
     */
    @Query("SELECT SUM(p.price * p.quantity) FROM Product p")
    BigDecimal calculateTotalInventoryValue();
    
    /**
     * Busca productos con stock disponible (cantidad > 0)
     * 
     * @return lista de productos con stock disponible
     */
    @Query("SELECT p FROM Product p WHERE p.quantity > 0")
    List<Product> findProductsInStock();
    
    /**
     * Busca productos por descripción (búsqueda parcial, insensible a mayúsculas)
     * 
     * @param description parte de la descripción a buscar
     * @return lista de productos que coinciden con la descripción
     */
    List<Product> findByDescriptionContainingIgnoreCase(String description);
}