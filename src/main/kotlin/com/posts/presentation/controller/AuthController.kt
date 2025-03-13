package com.posts.presentation.controller

import com.posts.application.dto.ApiResponseDto
import com.posts.application.service.AuthService
import io.jsonwebtoken.security.Keys
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(private val authService: AuthService) {

    @PostMapping("/register")
    fun register(@RequestBody request: RegisterRequest): ResponseEntity<ApiResponseDto<*>> {

        if (request.username.isBlank() || request.password.isBlank() || request.email.isBlank()) {
            throw org.apache.coyote.BadRequestException("Username, password, and email are required")
        }
        val response = authService.register(request.username, request.password, request.email)
        return ResponseEntity(response, HttpStatus.CREATED) // 201 Created
    }

    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): ResponseEntity<ApiResponseDto<*>> {
        if (request.username.isBlank() || request.password.isBlank()) {
            throw org.apache.coyote.BadRequestException("Username and password are required")
        }
        val response = authService.login(request.username, request.password)
        return ResponseEntity(response, HttpStatus.OK)
    }

    data class RegisterRequest(val username: String, val password: String, val email: String)
    data class LoginRequest(val username: String, val password: String)
}