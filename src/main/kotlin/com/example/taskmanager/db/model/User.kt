package com.example.taskmanager.db.model

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import java.util.UUID

@Entity
@Table(name = "users")

data class User(

    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "user_id", nullable = false, updatable = false)
    val userId: UUID? = null,

    @Column(nullable = false)
    @field:NotBlank(message = "Enter your name")
    var userName: String,

    @Column(unique = true, nullable = false)
    @field:NotBlank(message = "Enter your email")
    var email: String,

    @Column(nullable = false)
    @field:NotBlank(message = "Enter your password")
    var userPassword: String,

    )
