package com.posts.presentation

import io.jsonwebtoken.security.Keys
import java.util.Base64

fun main() {
    val secretKey = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS512)
    val base64Key = Base64.getEncoder().encodeToString(secretKey.encoded)
    println("Clave segura en Base64: $base64Key")
}