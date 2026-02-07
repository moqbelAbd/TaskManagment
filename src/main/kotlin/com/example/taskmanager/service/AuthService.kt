package com.example.taskmanager.service

import com.example.taskmanager.db.model.User
import com.example.taskmanager.dot.signIn.SignInRequest
import com.example.taskmanager.dot.signIn.SignInResponse
import com.example.taskmanager.dot.signUp.SignUpRequest
import com.example.taskmanager.dot.signUp.SignUpResponse
import com.example.taskmanager.repository.UserRepository
import com.example.taskmanager.security.JwtService
import jakarta.transaction.Transactional
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtService: JwtService
) {
    @Transactional
    fun signIn(request: SignInRequest): SignInResponse {
        val user = userRepository.findByEmail(request.email)

        if (user == null) {
            return SignInResponse(
                success = false,
                message = "User not found"
            )
        }

        if (!passwordEncoder.matches(request.password, user.userPassword)) {
            return SignInResponse(
                success = false,
                message = "Password incorrect"
            )
        }
        val token = jwtService.generateToken(user)
        return SignInResponse(
            success = true,
            message = "Login successful",
            userId = user.userId.toString(),
            token = token
        )
    }

    @Transactional
    fun signUp(request: SignUpRequest): SignUpResponse {

        if (userRepository.findByEmail(request.email) != null) {
            return SignUpResponse(
                success = false,
                message = "Email already exists"
            )
        }

        val hashedPassword = passwordEncoder.encode(request.password)

        val newUser = User(
            userName = request.fullName,
            email = request.email,
            userPassword = hashedPassword
        )

        val savedUser = userRepository.save(newUser)
        val token = jwtService.generateToken(newUser)
        return SignUpResponse(
            success = true,
            message = "User registered and logged in successfully",
            userId = newUser.userId.toString(),
            token = token
        )
    }
}