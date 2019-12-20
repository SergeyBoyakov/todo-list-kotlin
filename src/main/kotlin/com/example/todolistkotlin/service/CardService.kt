package com.example.todolistkotlin.service

import com.example.todolistkotlin.converter.CardConverter
import com.example.todolistkotlin.dto.CardDto
import com.example.todolistkotlin.repository.CardRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class CardService(
    private val cardRepository: CardRepository,
    private val converter: CardConverter
) {

    fun findAllCards(): List<CardDto> {
        val cards = cardRepository.findAllCards()

        return converter.toDtos(cards)
    }

    fun createCard(cardDto: CardDto): CardDto {
        val card = converter.toEntity(cardDto)
        val createdCard = cardRepository.createCard(card)

        return converter.toDto(createdCard)
    }

    fun updateCard(cardDto: CardDto, cardId: Long): CardDto {
        val card = cardRepository.findCardById(cardId)
        converter.populateEntity(cardDto, card)

        val savedCard = cardRepository.saveCard(card)

        return converter.toDto(savedCard)
    }

    fun deleteCardById(id: Long) = cardRepository.deleteCardById(id)
}