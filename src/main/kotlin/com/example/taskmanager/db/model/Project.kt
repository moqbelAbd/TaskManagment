package com.example.taskmanager.db.model


import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import java.util.UUID
import java.time.LocalDate


@Entity
@Table (name = "project")

data class Project(

    @Id
    @Column(name = "project_id", nullable = false)
    val projectId: UUID,

     @Column (nullable = false)
     @field:NotBlank(message = "Enter project name")
     var projectName: String,

     @Column
     var projectDescription: String? = null,

     @Column
     var startDate: LocalDate? = null,
    )
