package com.example.todolistkotlin.converter

import com.example.todolistkotlin.dto.CardDto
import com.example.todolistkotlin.model.Card
import org.springframework.stereotype.Service

@Service
class CardConverter(private val userConverter: UserConverter) {

    open fun toEntity(dto: CardDto): Card {
        return Card(
            title = dto.title,
            description = dto.description,
            creator = userConverter.toEntity(dto.creator)
        )
    }

    open fun toDto(entity: Card): CardDto {
        return CardDto().apply {
            id = entity.cardId
            title = entity.title
            description = entity.description
            creator =  userConverter.toDto(entity.creator)
        }
    }

    open fun populateEntity(dto: CardDto, entity: Card) {
        entity.title = dto.title
        entity.description = dto.description
        entity.creator = userConverter.toEntity(dto.creator)
    }

    open fun toDtos(entities: List<Card>): List<CardDto> = entities.map(this::toDto)
}

