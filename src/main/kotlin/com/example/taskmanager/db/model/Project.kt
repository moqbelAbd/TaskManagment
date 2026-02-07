package com.example.taskmanager.db.model


import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import java.util.UUID
import java.time.LocalDate


@Entity
@Table (name = "project")

data class Project(

    @Id
    @Column(name = "project_id", nullable = false )
    val projectId: UUID,

     @Column (nullable = false ,unique = true)
     @field:NotBlank(message = "Enter project name")
     var projectName: String,

     @Column
     var projectDescription: String? = null,

     @Column
     var startDate: LocalDate? = null,

    @Column(name = "owner_id", nullable = false) // Added Owner ID
    var ownerId: UUID,

    @OneToMany(mappedBy = "project", cascade = [CascadeType.ALL], orphanRemoval = true)
    val tasks: MutableList<Task> = mutableListOf()
    )
