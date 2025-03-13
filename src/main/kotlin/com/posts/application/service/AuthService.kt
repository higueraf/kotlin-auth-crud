package com.posts.application.service

import com.posts.application.dto.ApiResponseDto
import com.posts.domain.entity.User
import com.posts.domain.model.Role
import com.posts.domain.repository.UserRepository
import com.posts.infrastructure.security.CustomUserDetails
import com.posts.infrastructure.security.JwtUtil
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val authenticationManager: AuthenticationManager,
    @Qualifier("jwtUtilConfig") private val jwtUtil: JwtUtil, // Usa el calificador correcto
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {
    fun register(username: String, password: String, email: String): ApiResponseDto<User> {
        val user = User(
            username = username,
            password = passwordEncoder.encode(password),
            email = email,
            roles = setOf(Role.USER) // Default role is USER
        )
        val savedUser = userRepository.save(user)
        return ApiResponseDto(true, "User registered successfully", savedUser)
    }

    fun login(username: String, password: String): ApiResponseDto<String> {
        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(username, password)
        )
        val userDetails = authentication.principal as CustomUserDetails
        val userId = userDetails.uid
        val token = jwtUtil.generateToken(userId.toString(), username, username)
        return ApiResponseDto(true, "Login successful", token)
    }
}