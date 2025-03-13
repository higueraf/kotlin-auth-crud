package com.posts.application.dto

import com.fasterxml.jackson.annotation.JsonInclude

data class ApiResponseDto<T>(
    val success: Boolean,
    val message: String,
    val data: T? = null,

    @JsonInclude(JsonInclude.Include.NON_NULL)
    val debugMessage: String? = null
)