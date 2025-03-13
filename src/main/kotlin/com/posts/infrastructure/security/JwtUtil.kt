package com.posts.infrastructure.security

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import java.util.Date
import javax.crypto.SecretKey

class JwtUtil(private val secretKey: SecretKey, private val expirationTime: Long) {

    // Generar un token con uid, username y email
    fun generateToken(uid: String, username: String, email: String): String {
        return Jwts.builder()
            .setSubject(uid) // Usar uid como subject
            .claim("username", username) // Agregar username como claim
            .claim("email", email) // Agregar email como claim
            .setIssuedAt(Date()) // Fecha de emisión
            .setExpiration(Date(System.currentTimeMillis() + expirationTime)) // Fecha de expiración
            .signWith(secretKey, SignatureAlgorithm.HS512) // Firma con la clave secreta
            .compact() // Generar el token
    }

    // Validar el token
    fun validateToken(token: String): Boolean {
        return try {
            Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
            true
        } catch (e: Exception) {
            false
        }
    }

    // Extraer el uid (subject) del token
    fun extractUserId(token: String): String? {
        return Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
            .body
            .subject // Extraer el subject (uid)
    }

    // Extraer el username del token
    fun extractUsername(token: String): String? {
        return Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
            .body
            .get("username", String::class.java) // Extraer el claim "username"
    }

    // Extraer el email del token
    fun extractEmail(token: String): String? {
        return Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
            .body
            .get("email", String::class.java) // Extraer el claim "email"
    }
}