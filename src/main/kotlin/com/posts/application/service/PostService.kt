package com.posts.application.service

import com.posts.application.dto.ApiResponseDto
import com.posts.application.dto.PostDTO
import com.posts.presentation.exception.ResourceNotFoundException
import com.posts.domain.entity.Post
import com.posts.domain.repository.PostRepository
import org.modelmapper.ModelMapper
import org.springframework.stereotype.Service
import java.util.*

@Service
class PostService(
    private val postRepository: PostRepository,
    private val modelMapper: ModelMapper
) {
    fun createPost(postDTO: PostDTO): ApiResponseDto<Post> {
        val post = modelMapper.map(postDTO, Post::class.java)
        val savedPost = postRepository.save(post)
        return ApiResponseDto(true, "Post created successfully", savedPost)
    }

    fun getPosts(): ApiResponseDto<List<Post>> {
        val posts = postRepository.findAll()
        return ApiResponseDto(true, "Posts retrieved successfully", posts)
    }

    fun getPostById(id: UUID): ApiResponseDto<Post> {
        val post = postRepository.findById(id).orElseThrow {
            ResourceNotFoundException("Post not found with id: $id")
        }
        return ApiResponseDto(true, "Post retrieved successfully", post)
    }

    fun updatePost(id: UUID, postDTO: PostDTO): ApiResponseDto<Post> {
        // Buscar el post existente
        val existingPost = postRepository.findById(id).orElseThrow {
            ResourceNotFoundException("Post not found with id: $id")
        }

        existingPost.title = postDTO.title
        existingPost.content = postDTO.content

        val updatedPost = postRepository.save(existingPost)
        return ApiResponseDto(true, "Post updated successfully", updatedPost)
    }

    fun deletePost(id: UUID): ApiResponseDto<Unit> {
        if (!postRepository.existsById(id)) {
            throw ResourceNotFoundException("Post not found with id: $id")
        }
        postRepository.deleteById(id)
        return ApiResponseDto(true, "Post deleted successfully", null)
    }
}