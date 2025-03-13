package com.posts.application.service

import com.posts.application.dto.ApiResponseDto
import com.posts.application.dto.UserDTO
import com.posts.domain.entity.User
import com.posts.presentation.exception.ResourceNotFoundException
import com.posts.domain.repository.UserRepository
import org.modelmapper.ModelMapper
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(
    private val userRepository: UserRepository,
    private val modelMapper: ModelMapper
) {
    fun getAllUsers(): ApiResponseDto<List<User>> {
        val users = userRepository.findAll()
        return ApiResponseDto(true, "Users retrieved successfully", users)
    }

    fun getUserById(id: UUID): ApiResponseDto<UserDTO> {
        val user = userRepository.findById(id).orElseThrow {
            ResourceNotFoundException("User not found with id: $id")
        }
        val userDTO = modelMapper.map(user, UserDTO::class.java)
        return ApiResponseDto(true, "User retrieved successfully", userDTO)
    }

    fun updateUser(id: UUID, updatedUserDTO: UserDTO): ApiResponseDto<User> {
        val user = userRepository.findById(id).orElseThrow {
            ResourceNotFoundException("User not found with id: $id")
        }
        user.username = updatedUserDTO.username
        user.email = updatedUserDTO.email
        user.roles = updatedUserDTO.roles
        val savedUser = userRepository.save(user)
        return ApiResponseDto(true, "User updated successfully", savedUser)
    }

    fun deleteUser(id: UUID): ApiResponseDto<Unit> {
        if (!userRepository.existsById(id)) {
            throw ResourceNotFoundException("User not found with id: $id")
        }
        userRepository.deleteById(id)
        return ApiResponseDto(true, "User deleted successfully", null)
    }
}