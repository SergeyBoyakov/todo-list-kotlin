package com.example.todolistkotlin.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.util.*

@JsonIgnoreProperties(
    value = ["createdAt", "updatedAt"],
    allowGetters = true
)
class CardDto {
    var id: Long? = null
    var title: String? = null
    var description: String? = null
}