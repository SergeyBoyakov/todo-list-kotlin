package com.example.todolistkotlin.model

import javax.persistence.*


@Entity
data class Card(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long,

    @Column(name = "title")
    val title: String,

    @Column(name = "description")
    val description: String
)
