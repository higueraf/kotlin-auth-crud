package com.posts.infrastructure.security

import com.posts.domain.entity.User
import com.posts.domain.repository.UserRepository
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    private val userRepository: UserRepository
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByUsername(username)
            ?: throw UsernameNotFoundException("User not found with username: $username")

        // Convertir los roles a GrantedAuthority
        val authorities = user.roles.map { role ->
            SimpleGrantedAuthority("ROLE_"+role.name) // Usar el nombre del enum (ya incluye "ROLE_")
        }.toSet()

        return CustomUserDetails(
            uid = user.id!!,
            username = user.username,
            password = user.password,
            authorities = authorities
        )
    }
}