package com.example.todolistkotlin.converter

import com.example.todolistkotlin.dto.CardDto
import com.example.todolistkotlin.model.Card
import org.springframework.stereotype.Service

@Service
class CardConverter {

    open fun toEntity(dto: CardDto): Card {
        return Card(
            title = dto.title,
            description = dto.description
        )
    }

    open fun toDto(entity: Card): CardDto {
        return CardDto().apply {
            id = entity.id
            title = entity.title
            description = entity.description
        }
    }

    open fun populateEntity(dto: CardDto, entity: Card) {
        entity.title = dto.title
        entity.description = dto.description
    }

    open fun toDtos(entities: List<Card>): List<CardDto> = entities.map(this::toDto)
}

