package com.posts.application.dto

import java.util.*

data class PostDTO(
    val id: UUID? = null,
    val title: String,
    val content: String,
    val userId: UUID? = null
)