package com.posts.presentation.controller

import com.posts.application.dto.ApiResponseDto
import com.posts.application.dto.PostDTO
import com.posts.application.service.PostService
import com.posts.domain.entity.Post
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/posts")
class PostController(private val postService: PostService) {

    @PostMapping
    fun createPost(@RequestBody postDTO: PostDTO): ResponseEntity<ApiResponseDto<Post>> {
        val response = postService.createPost(postDTO)
        return ResponseEntity(response, HttpStatus.CREATED)
    }

    @GetMapping
    fun getPosts(): ResponseEntity<ApiResponseDto<List<Post>>> {
        val response = postService.getPosts()
        return ResponseEntity(response, HttpStatus.OK)
    }

    @GetMapping("/{id}")
    fun getPostById(@PathVariable id: UUID): ResponseEntity<ApiResponseDto<Post>> {
        val response = postService.getPostById(id)
        return ResponseEntity(response, HttpStatus.OK)
    }

    @PutMapping("/{id}")
    fun updatePost(
        @PathVariable id: UUID,
        @RequestBody postDTO: PostDTO
    ): ResponseEntity<ApiResponseDto<Post>> {
        val response = postService.updatePost(id, postDTO)
        return ResponseEntity(response, HttpStatus.OK)
    }


    @DeleteMapping("/{id}")
    fun deletePost(@PathVariable id: UUID): ResponseEntity<ApiResponseDto<Unit>> {
        val response = postService.deletePost(id)
        return ResponseEntity(response, HttpStatus.NO_CONTENT)
    }
}