package com.example.taskmanager.config

import com.example.taskmanager.db.model.Status
import com.example.taskmanager.repository.StatusRepository
import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Component


@Component
class DataInitializer(
    private val statusRepository: StatusRepository,
//    private val roleTypeRepository: RoleTypeRepository,

) {

    @PostConstruct
    fun init() {
        seedStates()
//        seedRoles()
    }

    private fun seedStates() {
        val existingStates = statusRepository.findAll().map { it.status }.toSet()

        val statesToInsert = listOf(
            "PENDING",
            "IN PROGRESS",
            "DONE"
        ).filter { it !in existingStates }

        val newStatuses = statesToInsert.map { Status(status = it) }

        if (newStatuses.isNotEmpty()) {
            statusRepository.saveAll(newStatuses)
            newStatuses.forEach { println("Seeded state: ${it.status}") }
        }
    }

//    private fun seedRoles() {
//        val existingRoles = roleTypeRepository.findAll().map { it.role }.toSet()
//
//        val rolesToInsert = ProjectRole.values()
//            .filter { it !in existingRoles }
//            .map { RoleType(role = it) }
//
//        if (rolesToInsert.isNotEmpty()) {
//            roleTypeRepository.saveAll(rolesToInsert)
//            rolesToInsert.forEach { println("Seeded role: ${it.role}") }
//        }
//    }


}