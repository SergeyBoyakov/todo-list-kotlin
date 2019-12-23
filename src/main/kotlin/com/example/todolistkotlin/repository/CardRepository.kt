package com.example.todolistkotlin.repository

import com.example.todolistkotlin.model.Card
import org.springframework.data.jpa.repository.JpaRepository

interface CardRepository : JpaRepository<Card, Long> {
    fun findCardByCardId(cardId: Long): Card?
}