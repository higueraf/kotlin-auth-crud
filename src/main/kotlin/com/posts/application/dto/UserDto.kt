package com.posts.application.dto

import com.posts.domain.model.Role
import java.util.*

data class UserDTO(
    val id: UUID? = null,
    val username: String,
    val email: String,
    val roles: Set<Role>
)