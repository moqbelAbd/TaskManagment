package com.example.taskmanager.dot.signOut

data class SignOutResponse(
    val success: Boolean,
    val message: String,
    val token: String? = null
)
