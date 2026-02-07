package com.example.taskmanager.dot.signIn

data class SignInResponse(
    val success: Boolean,
    val message: String,
    val userId: String? = null,
    val token: String? = null

)
