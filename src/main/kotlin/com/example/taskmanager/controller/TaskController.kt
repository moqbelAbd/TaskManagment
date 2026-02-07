package com.example.taskmanager.controller

import com.example.taskmanager.dot.task.CreateTaskRequest
import com.example.taskmanager.dot.task.UpdateTaskRequest
import com.example.taskmanager.service.TaskService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/api/tasks")
class TaskController(private val taskService: TaskService) {

    @PostMapping("/create")
    fun createTask(@RequestBody request: CreateTaskRequest): ResponseEntity<Map<String, Any>> {
        return try {
            val taskDto = taskService.createTask(request)
            ResponseEntity.ok(
                mapOf(
                    "success" to true,
                    "message" to "Task created successfully",
                    "data" to taskDto
                )
            )
        } catch (ex: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                mapOf(
                    "success" to false,
                    "message" to (ex.message ?: "Error creating task")
                )
            )
        }
    }

    @PutMapping("/update/{taskId}")
    fun updateTask(
        @PathVariable taskId: UUID,
        @RequestBody request: UpdateTaskRequest
    ): ResponseEntity<Map<String, Any>> {
        return try {
            val taskDto = taskService.updateTask(taskId, request)
            ResponseEntity.ok(
                mapOf(
                    "success" to true,
                    "message" to "Task updated successfully",
                    "data" to taskDto
                )
            )
        } catch (ex: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                mapOf(
                    "success" to false,
                    "message" to (ex.message ?: "Error updating task")
                )
            )
        }
    }
}