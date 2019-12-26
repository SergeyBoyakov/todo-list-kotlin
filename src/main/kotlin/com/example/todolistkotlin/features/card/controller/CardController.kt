package com.example.todolistkotlin.features.card.controller

import com.example.todolistkotlin.features.card.dto.CardDto
import com.example.todolistkotlin.features.card.service.CardService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("/cards")
class CardController(private val cardService: CardService) {

    @GetMapping
    fun findAllCards(): ResponseEntity<List<CardDto>> = ResponseEntity.ok(cardService.findAllCards())

    @PostMapping
    fun createCard(cardDto: CardDto): ResponseEntity<URI> {
        val createdCard = cardService.createCard(cardDto)
        val id = createdCard.id

        return ResponseEntity.created(URI.create("/cards/$id")).build()
    }

    @PutMapping("/{cardId}")
    fun updateCard(@RequestBody cardDto: CardDto, @PathVariable(value = "cardId") cardId: Long): ResponseEntity<CardDto> {
        cardService.updateCard(cardDto, cardId)

        return ResponseEntity.noContent().build()
    }

    @DeleteMapping("/{cardId}")
    fun deleteCard(@PathVariable(value = "cardId") cardId: Long): ResponseEntity<Long> {
        cardService.deleteCardById(cardId)

        return ResponseEntity.noContent().build()
    }
}