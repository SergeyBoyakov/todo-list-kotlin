package com.example.todolistkotlin.utils.creator

import com.example.todolistkotlin.dto.CardDto
import com.example.todolistkotlin.model.Card

fun getPredefinedCardEntity() =
    Card(title = "firstTitle", description = "firstDescription", creator = getPredefinedUserEntity())

fun getPredefinedCardDto() = CardDto().apply {
    title = "dto title"
    description = "dto desc"
    creator = getPredefinedUserDto()
}