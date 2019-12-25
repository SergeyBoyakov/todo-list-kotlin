package com.example.todolistkotlin.features.card.dto

class CardDto {
    var id: Long? = null
    var title: String? = null
    var description: String? = null
    lateinit var creator: String
}