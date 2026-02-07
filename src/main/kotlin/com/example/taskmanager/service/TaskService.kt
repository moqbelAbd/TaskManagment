package com.example.taskmanager.service

import com.example.taskmanager.db.enums.TaskStatus
import com.example.taskmanager.db.model.Task
import com.example.taskmanager.db.model.Status
import com.example.taskmanager.dot.task.CreateTaskRequest
import com.example.taskmanager.dot.task.TaskDto
import com.example.taskmanager.dot.task.UpdateTaskRequest
import com.example.taskmanager.repository.ProjectRepository
import com.example.taskmanager.repository.TaskRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.UUID

@Service
class TaskService(
    private val taskRepository: TaskRepository,
    private val projectRepository: ProjectRepository
) {

    @Transactional
    fun createTask(request: CreateTaskRequest): TaskDto {
        // 1. Validate Project Exists
        val project = projectRepository.findById(request.projectId)
            .orElseThrow { RuntimeException("Project not found with ID: ${request.projectId}") }

        // 2. Validate Title Length (Business Rule)
        if (request.title.length < 20) {
            throw IllegalArgumentException("Task title must be at least 50 characters long.")
        }

        // 3. Validate Due Date (Business Rule)
        if (request.dueDate.isBefore(LocalDate.now())) {
            throw IllegalArgumentException("Due date cannot be in the past.")
        }

        val task = Task(
            taskTitle = request.title,
            taskDescription = request.description,
            taskStatus = TaskStatus.PENDING,
            taskPriority = request.priority,
            taskDueDate = request.dueDate,
            project = project
        )

        val savedTask = taskRepository.save(task)
        return mapToDto(savedTask)
    }

    @Transactional
    fun updateTask(taskId: UUID, request: UpdateTaskRequest): TaskDto {
        val task = taskRepository.findById(taskId)
            .orElseThrow { RuntimeException("Task not found") }

        // Update fields if they are provided (not null)
        request.title?.let {
            if (it.length < 50) throw IllegalArgumentException("Task title must be at least 50 characters long.")
            task.taskTitle = it
        }
        request.description?.let { task.taskDescription = it }
        request.priority?.let { task.taskPriority = it }
        request.dueDate?.let {
            if (it.isBefore(LocalDate.now())) throw IllegalArgumentException("Due date cannot be in the past.")
            task.taskDueDate = it
        }

        // Workflow Logic: Check Status Transition
        request.status?.let { newStatus ->
            if (task.taskStatus == TaskStatus.PENDING && newStatus == TaskStatus.DONE) {
                throw IllegalArgumentException("Workflow violation: Cannot move directly from PENDING to DONE. Must go to IN_PROGRESS first.")
            }
            task.taskStatus = newStatus
        }

        val updatedTask = taskRepository.save(task)
        return mapToDto(updatedTask)
    }

    // Helper to convert Entity -> DTO
    private fun mapToDto(task: Task): TaskDto {
        return TaskDto(
            id = task.taskId!!,
            title = task.taskTitle,
            description = task.taskDescription,
            status = task.taskStatus,
            priority = task.taskPriority,
            dueDate = task.taskDueDate,
            projectId = task.project.projectId
        )
    }
}