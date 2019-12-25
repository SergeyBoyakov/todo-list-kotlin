package com.example.todolistkotlin.features.card.converter

import com.example.todolistkotlin.features.user.converter.UserConverter
import com.example.todolistkotlin.features.card.dto.CardDto
import com.example.todolistkotlin.features.card.model.Card
import org.springframework.stereotype.Service

@Service
class CardConverter {

    open fun toEntity(dto: CardDto): Card {
        return Card(
            title = dto.title,
            description = dto.description,
            creator = dto.creator
        )
    }

    open fun toDto(entity: Card): CardDto {
        return CardDto().apply {
            id = entity.cardId
            title = entity.title
            description = entity.description
            creator = entity.creator
        }
    }

    open fun populateEntity(dto: CardDto, entity: Card) {
        entity.title = dto.title
        entity.description = dto.description
    }

    open fun toDtos(entities: List<Card>): List<CardDto> = entities.map(this::toDto)
}

