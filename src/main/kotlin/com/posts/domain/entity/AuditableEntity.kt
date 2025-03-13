package com.posts.domain.entity


import org.springframework.data.annotation.*
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import java.util.*
import jakarta.persistence.*
import jakarta.persistence.Id

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: UUID? = null

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    var createdAt: LocalDateTime? = null

    @LastModifiedDate
    @Column(name = "updated_at")
    var updatedAt: LocalDateTime? = null

    @CreatedBy
    @Column(name = "created_by", updatable = false)
    var createdBy: UUID? = null

    @LastModifiedBy
    @Column(name = "updated_by")
    var updatedBy: UUID? = null

    @Column(name = "deleted_at")
    var deletedAt: LocalDateTime? = null

    @Column(name = "deleted_by")
    var deletedBy: UUID? = null
}