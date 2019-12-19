package com.example.todolistkotlin.controller

import com.example.todolistkotlin.dto.CardDto
import com.example.todolistkotlin.service.CardService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI


@RestController
@RequestMapping("/cards")
class CardController(private val cardService: CardService) {

    @GetMapping
    fun findAllCards(): ResponseEntity<List<CardDto>> {
        cardService.findAllCards()

        return ResponseEntity.ok(listOf())
    }

    @PostMapping
    fun createCard(cardDto: CardDto): ResponseEntity<URI> {
        val createCard = cardService.createCard(cardDto)
        val id = createCard.id

        return ResponseEntity.created(URI.create("/cards/$id")).build()
    }

    @PutMapping
    fun updateCard(@RequestBody cardDto: CardDto, cardId: Long): ResponseEntity<CardDto> {
        cardService.updateCard(cardDto, cardId)

        return ResponseEntity.noContent().build()
    }

    @DeleteMapping
    fun deleteCard(cardId: Long): ResponseEntity<Long> {
        cardService.deleteCardById(cardId)

        return ResponseEntity.noContent().build()
    }
}