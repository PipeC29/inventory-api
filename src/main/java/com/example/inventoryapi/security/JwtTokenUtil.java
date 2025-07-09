package com.example.inventoryapi.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Utilidad para operaciones con tokens JWT
 * 
 * Esta clase maneja la generación, validación y extracción de información
 * de tokens JWT utilizados para autenticación.
 * 
 * @author Sistema de Inventario
 * @version 1.0.0
 */
@Component
public class JwtTokenUtil {
    
    @Value("${jwt.secret}")
    private String secret;
    
    @Value("${jwt.expiration}")
    private Long expiration;
    
    /**
     * Extrae el nombre de usuario del token JWT
     * 
     * @param token token JWT
     * @return nombre de usuario
     */
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }
    
    /**
     * Extrae la fecha de expiración del token JWT
     * 
     * @param token token JWT
     * @return fecha de expiración
     */
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }
    
    /**
     * Extrae una claim específica del token JWT
     * 
     * @param token token JWT
     * @param claimsResolver función para extraer la claim
     * @return valor de la claim
     */
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }
    
    /**
     * Extrae todas las claims del token JWT
     * 
     * @param token token JWT
     * @return todas las claims
     */
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    
    /**
     * Verifica si el token JWT ha expirado
     * 
     * @param token token JWT
     * @return true si ha expirado, false en caso contrario
     */
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }
    
    /**
     * Genera un nuevo token JWT para el usuario
     * 
     * @param username nombre de usuario
     * @return token JWT generado
     */
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }
    
    /**
     * Crea un token JWT con las claims especificadas
     * 
     * @param claims claims a incluir en el token
     * @param subject sujeto del token (usuario)
     * @return token JWT creado
     */
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    
    /**
     * Valida un token JWT
     * 
     * @param token token JWT a validar
     * @param username nombre de usuario esperado
     * @return true si el token es válido, false en caso contrario
     */
    public Boolean validateToken(String token, String username) {
        try {
            final String tokenUsername = getUsernameFromToken(token);
            return (tokenUsername.equals(username) && !isTokenExpired(token));
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
    
    /**
     * Valida un token JWT sin verificar el usuario
     * 
     * @param token token JWT a validar
     * @return true si el token es válido, false en caso contrario
     */
    public Boolean validateToken(String token) {
        try {
            getAllClaimsFromToken(token);
            return !isTokenExpired(token);
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
    
    /**
     * Obtiene la clave de firma para los tokens JWT
     * 
     * @return clave de firma
     */
    private SecretKey getSigningKey() {
        byte[] keyBytes = secret.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }
    
    /**
     * Obtiene el tiempo de expiración configurado
     * 
     * @return tiempo de expiración en milisegundos
     */
    public Long getExpiration() {
        return expiration;
    }
}