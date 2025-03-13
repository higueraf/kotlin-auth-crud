package com.posts.domain.entity

import com.posts.domain.model.Role
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "users")
data class User(
    var username: String,
    var password: String,
    var email: String,

    @ElementCollection(targetClass = Role::class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = [JoinColumn(name = "user_id")])
    @Enumerated(EnumType.STRING)
    var roles: Set<Role> = emptySet()
) : AuditableEntity()