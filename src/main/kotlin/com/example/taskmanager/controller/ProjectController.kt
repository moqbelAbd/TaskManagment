package com.example.taskmanager.controller


import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import com.example.taskmanager.service.ProjectService
import com.example.taskmanager.dot.*
import com.example.taskmanager.dot.project.CreateProjectRequest
import com.example.taskmanager.security.JwtService
import java.util.UUID

@RestController
@RequestMapping("/api/projects")

class ProjectController (private val projectService: ProjectService ,
                         private val jwtService: JwtService) {
    @PostMapping("/create")
    fun createProject(
        @RequestBody request: CreateProjectRequest,
        @RequestHeader("Authorization") authHeader: String
    ): ResponseEntity<Map<String, Any>> {
        return try {
            val token = authHeader.removePrefix("Bearer ").trim()
            val ownerId = jwtService.extractUserId(token) ?: throw RuntimeException("Invalid Token")

            // 1. Call the service and get the result (which includes projectId)
            val serviceResponse = projectService.createProject(request, ownerId)

            // 2. Return that full response to the client
            ResponseEntity.ok(serviceResponse)

        } catch (ex: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                mapOf(
                    "success" to false,
                    "message" to (ex.message ?: "Unknown error occurred")
                )
            )
        }
    }

    @DeleteMapping("/delete/{projectId}")
    fun deleteProject(@PathVariable projectId: UUID): ResponseEntity<Map<String, Any>> {
        return try {
            projectService.deleteProject(projectId)
            ResponseEntity.ok(
                mapOf("success" to true, "message" to "Project and all associated tasks deleted successfully")
            )
        } catch (ex: Exception) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                mapOf("success" to false, "message" to (ex.message ?: "Error deleting project"))
            )
        }
    }
}