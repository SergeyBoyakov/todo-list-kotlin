package com.example.todolistkotlin.dto

class CardDto {
    var id: Long? = null
    var title: String? = null
    var description: String? = null
    lateinit var creator: UserDto
}