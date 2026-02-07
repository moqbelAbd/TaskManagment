package com.example.taskmanager.dot.task

import com.example.taskmanager.db.enums.TaskPriority
import com.example.taskmanager.db.enums.TaskStatus
import java.time.LocalDate
import java.util.UUID

data class CreateTaskRequest(
    val projectId: UUID, // We need to know which project this task belongs to
    val title: String,
    val description: String? = null,
    val priority: TaskPriority = TaskPriority.MEDIUM,
    val dueDate: LocalDate
)
