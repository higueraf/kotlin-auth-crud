package com.posts.infrastructure.config

import com.posts.infrastructure.security.CustomUserDetails
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.AuditorAware
import org.springframework.security.core.context.SecurityContextHolder
import java.util.*

@Configuration
class AuditConfig {

    @Bean
    fun auditorAware(): AuditorAware<UUID> {
        return AuditorAware {
            val authentication = SecurityContextHolder.getContext().authentication
            if (authentication != null && authentication.isAuthenticated) {
                val userId = when (val principal = authentication.principal) {
                    is CustomUserDetails -> principal.uid
                    is String -> try {
                        UUID.fromString(principal)
                    } catch (e: IllegalArgumentException) {
                        null
                    }
                    else -> null
                }
                Optional.ofNullable(userId)
            } else {
                Optional.empty()
            }
        }
    }
}