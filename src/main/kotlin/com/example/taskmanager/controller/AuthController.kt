package com.example.taskmanager.controller

import com.example.taskmanager.dot.signIn.SignInRequest
import com.example.taskmanager.dot.signIn.SignInResponse
import com.example.taskmanager.dot.signOut.SignOutResponse
import com.example.taskmanager.dot.signUp.SignUpRequest
import com.example.taskmanager.dot.signUp.SignUpResponse
import com.example.taskmanager.service.AuthService
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/auth")

class AuthController(private val authService: AuthService) {

    @PostMapping("/signin")
    fun signIn(
        @RequestBody request: SignInRequest
    ): SignInResponse {
        val signInResponse = authService.signIn(request)

        return SignInResponse(
            success = signInResponse.success,
            token = signInResponse.token,
            message = signInResponse.message,
            userId = signInResponse.userId
        )
    }

    @PostMapping("/signup")
    fun signup(
        @RequestBody request: SignUpRequest
    ): SignUpResponse {
        val signUpResponse = authService.signUp(request)

        return SignUpResponse(
            success = signUpResponse.success,
            token = signUpResponse.token,
            message = signUpResponse.message,
            userId = signUpResponse.userId
        )
    }

    @PostMapping("/logout")
    fun logout(response: HttpServletResponse): SignOutResponse {

        return SignOutResponse(
            success = true,
            message = "Logged out successfully.",
        )
    }
}
