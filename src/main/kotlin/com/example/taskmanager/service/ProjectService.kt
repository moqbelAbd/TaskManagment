package com.example.taskmanager.service

import com.example.taskmanager.db.model.Project
import com.example.taskmanager.dot.project.CreateProjectRequest
import com.example.taskmanager.repository.ProjectRepository
import com.example.taskmanager.repository.TaskRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.UUID

@Service
open class ProjectService (
    private val projectRepository: ProjectRepository ,
    private val taskRepository: TaskRepository )
{


    @Transactional
    open fun createProject(request: CreateProjectRequest, ownerId: UUID): Map<String, Any> {

        if (projectRepository.existsByProjectName(request.projectName)) {
            throw IllegalArgumentException("Project with name '${request.projectName}' already exists")
        }

        val project = Project(
            projectId = UUID.randomUUID(),
            projectName = request.projectName,
            projectDescription = request.projectDescription ?: "No description provided",
            ownerId = ownerId,
            startDate = LocalDate.now(),
            )

//        projectRepository.save(project)

//        val AdminRole = roleTypeRepository.findByRole(ProjectRole.MASTER)
//            ?: throw RuntimeException("MASTER role not found")
//
        val savedProject = projectRepository.save(project)

        return mapOf(
            "success" to true,
            "message" to "Project created successfully",
            "projectId" to savedProject.projectId
        )
    }


    @Transactional
    open fun deleteProject(projectId: UUID) {
        if (!projectRepository.existsById(projectId)) {
            throw RuntimeException("Project not found")
        }
        projectRepository.deleteById(projectId)
    }
}