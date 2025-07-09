package com.example.inventoryapi.controller;

import com.example.inventoryapi.dto.AuthRequestDto;
import com.example.inventoryapi.dto.AuthResponseDto;
import com.example.inventoryapi.security.CustomUserDetailsService;
import com.example.inventoryapi.security.JwtTokenUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador para operaciones de autenticación
 * 
 * Este controlador maneja el login de usuarios y la generación de tokens JWT.
 * 
 * @author Sistema de Inventario
 * @version 1.0.0
 */
@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticación", description = "API para autenticación y autorización")
public class AuthController {
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    
    @Autowired
    private CustomUserDetailsService userDetailsService;
    
    /**
     * Endpoint para autenticación de usuarios
     * 
     * @param authRequest credenciales de usuario
     * @return token JWT si la autenticación es exitosa
     */
    @PostMapping("/login")
    @Operation(summary = "Autenticar usuario", 
               description = "Autentica un usuario y devuelve un token JWT válido")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Autenticación exitosa"),
        @ApiResponse(responseCode = "401", description = "Credenciales inválidas"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    public ResponseEntity<?> login(@Valid @RequestBody AuthRequestDto authRequest) {
        
        try {
            // Intentar autenticar al usuario
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    authRequest.getUsername(), 
                    authRequest.getPassword()
                )
            );
            
            // Si la autenticación es exitosa, generar el token JWT
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = jwtTokenUtil.generateToken(userDetails.getUsername());
            
            AuthResponseDto response = new AuthResponseDto(
                token,
                userDetails.getUsername(),
                jwtTokenUtil.getExpiration()
            );
            
            return ResponseEntity.ok(response);
            
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponse("Credenciales inválidas", "INVALID_CREDENTIALS"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("Error interno del servidor", "INTERNAL_ERROR"));
        }
    }
    
    /**
     * Endpoint para obtener información del usuario actual
     * 
     * @param token token JWT del usuario
     * @return información del usuario
     */
    @GetMapping("/me")
    @Operation(summary = "Obtener información del usuario actual",
               description = "Devuelve información del usuario autenticado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Información obtenida exitosamente"),
        @ApiResponse(responseCode = "401", description = "Token JWT requerido")
    })
    public ResponseEntity<?> getCurrentUser(@RequestHeader("Authorization") String token) {
        
        try {
            // Extraer el token del header Authorization
            String jwtToken = token.substring(7); // Remover "Bearer "
            String username = jwtTokenUtil.getUsernameFromToken(jwtToken);
            
            if (jwtTokenUtil.validateToken(jwtToken, username)) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                
                return ResponseEntity.ok(new UserInfo(
                    userDetails.getUsername(),
                    userDetails.getAuthorities().stream()
                        .map(auth -> auth.getAuthority())
                        .toList()
                ));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse("Token inválido", "INVALID_TOKEN"));
            }
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponse("Token inválido", "INVALID_TOKEN"));
        }
    }
    
    /**
     * Endpoint para refrescar el token JWT
     * 
     * @param token token JWT actual
     * @return nuevo token JWT
     */
    @PostMapping("/refresh")
    @Operation(summary = "Refrescar token JWT",
               description = "Genera un nuevo token JWT a partir del token actual")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Token refrescado exitosamente"),
        @ApiResponse(responseCode = "401", description = "Token JWT requerido o inválido")
    })
    public ResponseEntity<?> refreshToken(@RequestHeader("Authorization") String token) {
        
        try {
            String jwtToken = token.substring(7); // Remover "Bearer "
            String username = jwtTokenUtil.getUsernameFromToken(jwtToken);
            
            if (jwtTokenUtil.validateToken(jwtToken, username)) {
                String newToken = jwtTokenUtil.generateToken(username);
                
                AuthResponseDto response = new AuthResponseDto(
                    newToken,
                    username,
                    jwtTokenUtil.getExpiration()
                );
                
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse("Token inválido", "INVALID_TOKEN"));
            }
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponse("Token inválido", "INVALID_TOKEN"));
        }
    }
    
    /**
     * Clase para respuestas de error
     */
    public static class ErrorResponse {
        private String message;
        private String code;
        
        public ErrorResponse(String message, String code) {
            this.message = message;
            this.code = code;
        }
        
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
        public String getCode() { return code; }
        public void setCode(String code) { this.code = code; }
    }
    
    /**
     * Clase para información del usuario
     */
    public static class UserInfo {
        private String username;
        private java.util.List<String> authorities;
        
        public UserInfo(String username, java.util.List<String> authorities) {
            this.username = username;
            this.authorities = authorities;
        }
        
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public java.util.List<String> getAuthorities() { return authorities; }
        public void setAuthorities(java.util.List<String> authorities) { this.authorities = authorities; }
    }
}