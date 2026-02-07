package com.example.taskmanager.repository

import com.example.taskmanager.db.model.Task
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface TaskRepository : JpaRepository<Task, UUID> {
//     fun findByProjectId(projectId: UUID): List<Task>
}