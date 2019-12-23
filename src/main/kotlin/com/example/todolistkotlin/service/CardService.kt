package com.example.todolistkotlin.service

import com.example.todolistkotlin.converter.CardConverter
import com.example.todolistkotlin.dto.CardDto
import com.example.todolistkotlin.exception.CardNotFoundException
import com.example.todolistkotlin.model.Card
import com.example.todolistkotlin.repository.CardRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CardService(
    private val cardRepository: CardRepository,
    private val converter: CardConverter
) {

    fun findAllCards(): List<CardDto> {
        val cards = cardRepository.findAll()

        return converter.toDtos(cards)
    }

    fun findCardEntityById(cardId: Long): Card = cardRepository.findById(cardId)
        .orElseThrow { CardNotFoundException(cardId) }

    fun createCard(cardDto: CardDto): CardDto {
        val card = converter.toEntity(cardDto)
        val createdCard = cardRepository.save(card)

        return converter.toDto(createdCard)
    }

    fun updateCard(cardDto: CardDto, cardId: Long): CardDto {
        val card = this.findCardEntityById(cardId)
        converter.populateEntity(cardDto, card)

        val savedCard = cardRepository.save(card)

        return converter.toDto(savedCard)
    }

    fun deleteCardById(id: Long) = cardRepository.deleteById(id)
}