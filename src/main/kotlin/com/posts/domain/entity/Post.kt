package com.posts.domain.entity

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "posts")
data class Post(
    var title: String = "",
    var content: String = "",
    var userId: UUID? = null
) : AuditableEntity()