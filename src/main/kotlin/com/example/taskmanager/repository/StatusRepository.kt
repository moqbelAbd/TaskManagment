package com.example.taskmanager.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import com.example.taskmanager.db.model.Status

@Repository
interface StatusRepository : JpaRepository<Status, Long> {


    fun findByStatus(name: String): Status?
}
