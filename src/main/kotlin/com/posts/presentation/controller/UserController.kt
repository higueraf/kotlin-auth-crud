package com.posts.presentation.controller

import com.posts.application.dto.ApiResponseDto
import com.posts.application.dto.UserDTO
import com.posts.application.service.UserService
import com.posts.domain.entity.User
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/users")
class UserController(private val userService: UserService) {

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    fun getAllUsers(): ResponseEntity<ApiResponseDto<List<User>>> {
        val response = userService.getAllUsers()
        return ResponseEntity(response, HttpStatus.OK) // 200 OK
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    fun getUserById(@PathVariable id: UUID): ResponseEntity<ApiResponseDto<UserDTO>> {
        val response = userService.getUserById(id)
        return ResponseEntity(response, HttpStatus.OK) // 200 OK
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    fun updateUser(@PathVariable id: UUID, @RequestBody updatedUserDTO: UserDTO): ResponseEntity<ApiResponseDto<User>> {
        val response = userService.updateUser(id, updatedUserDTO)
        return ResponseEntity(response, HttpStatus.OK) // 200 OK
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    fun deleteUser(@PathVariable id: UUID): ResponseEntity<ApiResponseDto<Unit>> {
        val response = userService.deleteUser(id)
        return ResponseEntity(response, HttpStatus.NO_CONTENT) // 204 No Content
    }
}