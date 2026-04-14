package com.proyecto.postremaniacapi.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    // 🔥 mínimo 32 caracteres
    private final String SECRET = "postremaniac_secret_key_2026_super_segura";

    private SecretKey getKey(){
        return Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    }

    // 🔥 AHORA INCLUYE EMAIL
    public String generateToken(String id, String username, String rol, String email){

        return Jwts.builder()
                .setSubject(username)
                .claim("id", id)
                .claim("rol", rol)
                .claim("email", email) // 🔥 NUEVO
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(getKey())
                .compact();
    }

    // 🔍 USERNAME
    public String extractUsername(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // 🔍 ROL
    public String extractRol(String token){
        return (String) Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("rol");
    }

    // 🔍 ID
    public String extractUserId(String token){
        return (String) Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("id");
    }

    // 🔍 EMAIL
    public String extractEmail(String token){
        return (String) Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("email");
    }

    // 🔐 VALIDAR TOKEN
    public boolean validateToken(String token){
        try{
            Jwts.parserBuilder()
                    .setSigningKey(getKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        }catch(Exception e){
            System.out.println("Error validando token: " + e.getMessage());
            return false;
        }
    }
}