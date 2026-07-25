package com.example.taskmanager.security

import com.example.taskmanager.db.model.User
import com.example.taskmanager.repository.UserRepository
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Service
import java.util.Date
import java.util.UUID

@Service
class JwtService(private val userRepository: UserRepository) {

    private val secretKey = "H6GfP2L4c9T8Wq9Xv3B1R8yL0pZ6R7wQm5N2c4V8b2F6S7M8K3L0T1Q2W3E4R5T"
    private val key = Keys.hmacShaKeyFor(secretKey.toByteArray())
    private val expiration = 1000 * 60 * 60 * 24L // 24 ساعة

    fun generateToken(user: User): String {
        return Jwts.builder()
            .setSubject(user.userId.toString())
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + expiration))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()
    }

    fun extractUserId(token: String): UUID {
        val claims = Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body
        return UUID.fromString(claims.subject)
    }

    fun getUserFromToken(token: String): User? {
        val userId = extractUserId(token)
        return userRepository.findByUserId(userId)
    }
}
