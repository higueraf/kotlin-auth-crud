package com.posts.infrastructure.config

import com.posts.application.dto.ApiResponseDto
import com.posts.presentation.exception.ErrorDetails
import com.posts.presentation.exception.ResourceNotFoundException
import com.posts.presentation.exception.BadRequestException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleResourceNotFoundException(ex: ResourceNotFoundException, request: WebRequest): ResponseEntity<ApiResponseDto<ErrorDetails>> {
        val errorDetails = ErrorDetails(message = ex.message ?: "Resource not found", details = request.getDescription(false))
        val response = ApiResponseDto(false, "Resource not found", errorDetails)
        return ResponseEntity(response, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(BadRequestException::class)
    fun handleBadRequestException(ex: BadRequestException, request: WebRequest): ResponseEntity<ApiResponseDto<ErrorDetails>> {
        val errorDetails = ErrorDetails(message = ex.message ?: "Bad request", details = request.getDescription(false))
        val response = ApiResponseDto(false, "Bad request", errorDetails)
        return ResponseEntity(response, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(Exception::class)
    fun handleGlobalException(ex: Exception, request: WebRequest): ResponseEntity<ApiResponseDto<ErrorDetails>> {
        val errorDetails = ErrorDetails(message = ex.message ?: "Internal server error", details = request.getDescription(false))
        val response = ApiResponseDto(false, "Internal server error", errorDetails)
        return ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}
