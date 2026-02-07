package com.example.taskmanager.config

import com.example.taskmanager.db.model.State
import com.example.taskmanager.repository.StateRepository
import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Component


@Component
class DataInitializer(
    private val stateRepository: StateRepository,
//    private val roleTypeRepository: RoleTypeRepository,

) {

    @PostConstruct
    fun init() {
        seedStates()
//        seedRoles()
    }

    private fun seedStates() {
        val existingStates = stateRepository.findAll().map { it.state }.toSet()

        val statesToInsert = listOf(
            "PENDING",
            "IN PROGRESS",
            "DONE"
        ).filter { it !in existingStates }

        val newStates = statesToInsert.map { State(state = it) }

        if (newStates.isNotEmpty()) {
            stateRepository.saveAll(newStates)
            newStates.forEach { println("Seeded state: ${it.state}") }
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