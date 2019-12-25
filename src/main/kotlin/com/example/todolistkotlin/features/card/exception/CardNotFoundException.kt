package com.example.todolistkotlin.features.card.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException

@ResponseStatus(HttpStatus.NOT_FOUND)
class CardNotFoundException(cardId: Long) : RuntimeException("Card with id: $cardId not found")



