package com.posts.presentation.exception

import java.time.LocalDateTime

data class ErrorDetails(
    val timestamp: LocalDateTime = LocalDateTime.now(),
    val message: String,
    val details: String
)