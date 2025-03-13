package com.posts.domain.repository

import com.posts.domain.entity.Post
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface PostRepository : JpaRepository<Post, UUID>