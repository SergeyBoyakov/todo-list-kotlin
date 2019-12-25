package com.example.todolistkotlin.utils.creator

import com.example.todolistkotlin.features.card.dto.CardDto
import com.example.todolistkotlin.features.card.model.Card

fun getPredefinedCardEntity() =
    Card(
        title = "firstTitle",
        description = "firstDescription",
        creator = "creatorentity@gmail.com"
    )

fun getPredefinedCardDto() = CardDto().apply {
    title = "dto title"
    description = "dto desc"
    creator = "creatordto@gmail.com"
}