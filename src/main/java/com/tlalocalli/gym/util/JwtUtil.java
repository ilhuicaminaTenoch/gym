package com.tlalocalli.gym.util;

import com.tlalocalli.gym.persistence.enums.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    // Generamos una clave segura para HS256
    @Value("${jwt.secret}")
    private String secret ;

    // Tiempo de expiración en milisegundos (por ejemplo, 1 hora)
    private final long JWT_EXPIRATION = 3600000;

    private Key key;

    @PostConstruct
    public void init() {
        // Se decodifica la clave en Base64 y se crea el Key para HS256 (se requieren al menos 32 bytes)
        key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    // Genera un token JWT para el usuario (subject)
    public String generateToken(
            String username,
            Integer userId,
            String fullName,
            Role role,
            String email
            ) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("fullName", fullName);
        claims.put("role", role);
        claims.put("email", email);
        return createToken(claims, username);
    }

    private String createToken(Map<String, Object> claims, String subject) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key)
                .compact();
    }

    // Extrae el username (subject) del token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Extrae un claim específico usando la función proporcionada
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claimsResolver.apply(claims);
    }

    // Valida que el token pertenezca al usuario y no haya expirado
    public boolean validateToken(String token, String username) {
        final String tokenUsername = extractUsername(token);
        return (tokenUsername.equals(username) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        final Date expiration = extractClaim(token, Claims::getExpiration);
        return expiration.before(new Date());
    }
}
