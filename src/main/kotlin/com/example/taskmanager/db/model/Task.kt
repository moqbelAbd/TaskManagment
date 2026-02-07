package com.example.taskmanager.db.model

import jakarta.persistence.Column
import jakarta.persistence.ElementCollection
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.validation.constraints.NotBlank
import java.time.LocalDate
import java.util.UUID

data class Task(

    @Id
    @Column
    val taskId: UUID=UUID.randomUUID(),


    @Column (nullable = false)
    @field:NotBlank(message = "Enter task title")
    var taskTitle: String,

    @Column(length = 500)
    var taskDescription: String? = null,

    @Column
    var taskDueDate: LocalDate? = null,

    @Column
    var taskPriority: String? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productBacklog_id")
    val project: Project,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "state_id") //Enum
    var stateID: State,

)
