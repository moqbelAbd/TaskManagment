package com.example.taskmanager.dot.signUp

data class SignUpResponse(
    val success: Boolean,
    val message: String,
    val userId: String? = null,
    val token: String? = null
)
