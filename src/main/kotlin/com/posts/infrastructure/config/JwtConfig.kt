package com.posts.infrastructure.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import com.posts.infrastructure.security.JwtUtil
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import java.util.Base64
import org.springframework.beans.factory.annotation.Qualifier

@Configuration
class JwtConfig {

    @Value("\${jwt.secret}")
    lateinit var jwtSecretKey: String

    @Value("\${jwt.expiration}")
    var jwtExpiration: Long = 0

    @Bean
    @Qualifier("jwtUtilConfig") // Agrega esta línea
    fun jwtUtil(): JwtUtil {
        // Generar la clave adecuada para HS512
        val secretKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(jwtSecretKey))

        if (jwtExpiration == 0L) {
            throw IllegalStateException("JWT_EXPIRATION is not set correctly")
        }

        return JwtUtil(secretKey, jwtExpiration)
    }
}