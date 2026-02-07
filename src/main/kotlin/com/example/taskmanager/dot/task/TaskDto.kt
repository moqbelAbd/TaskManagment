package com.example.taskmanager.dot.task

import com.example.taskmanager.db.enums.TaskPriority
import com.example.taskmanager.db.enums.TaskStatus
import java.time.LocalDate
import java.util.UUID

data class TaskDto(
    val id: UUID,
    val title: String,
    val description: String?,
    val status: TaskStatus,
    val priority: TaskPriority,
    val dueDate: LocalDate?,
    val projectId: UUID
)
