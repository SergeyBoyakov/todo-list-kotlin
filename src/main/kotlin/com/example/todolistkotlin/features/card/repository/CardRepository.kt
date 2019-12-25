package com.example.todolistkotlin.features.card.repository

import com.example.todolistkotlin.features.card.model.Card
import org.springframework.data.jpa.repository.JpaRepository

interface CardRepository : JpaRepository<Card, Long> {
    fun findCardByCardId(cardId: Long): Card?
}