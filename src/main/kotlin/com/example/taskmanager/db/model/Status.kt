package com.example.taskmanager.db.model

import jakarta.persistence.*


@Entity
@Table (name ="state")

data class Status(


    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val stateID: Long=0L,

    @Column (nullable = false)
    var status: String="",

)
