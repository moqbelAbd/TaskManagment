package com.example.taskmanager.db.model

import com.example.taskmanager.db.enums.TaskPriority
import com.example.taskmanager.db.enums.TaskStatus
import jakarta.persistence.Column
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import jakarta.validation.constraints.NotBlank
import java.time.LocalDate
import java.util.UUID

@Entity
@Table (name = "task")

data class Task(

    @Id
    @Column
    val taskId: UUID=UUID.randomUUID(),


    @Column (nullable = false ,length = 500)
    @field:NotBlank(message = "Enter task title")
    var taskTitle: String,

    @Column(length = 500)
    var taskDescription: String? = null,

    @Column
    var taskDueDate: LocalDate? = null,

    @Enumerated(EnumType.STRING)
    var taskStatus: TaskStatus = TaskStatus.PENDING, // Default status

    @Enumerated(EnumType.STRING)
    var taskPriority: TaskPriority,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    var project: Project

    )
