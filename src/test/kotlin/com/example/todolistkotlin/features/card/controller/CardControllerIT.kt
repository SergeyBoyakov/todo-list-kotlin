package com.example.todolistkotlin.features.card.controller

import com.example.todolistkotlin.features.card.dto.CardDto
import com.example.todolistkotlin.features.card.service.CardService
import com.example.todolistkotlin.utils.creator.getPredefinedCardDto
import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(controllers = [CardController::class])
internal class CardControllerIT {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockkBean(relaxed = true)
    private lateinit var cardService: CardService

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Test
    fun `should return all cards`() {
        // given:
        every { cardService.findAllCards() } returns listOf(
            getPredefinedCardDto().apply { id = 1L },
            getPredefinedCardDto().apply { id = 2L })

        // when:
        mockMvc.perform(
            get("/cards")
                .contentType(MediaType.APPLICATION_JSON)
        )
            // then:
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].id", `is`(1)))
            .andExpect(jsonPath("$[1].id", `is`(2)))
    }

    @Test
    fun `should create card`() {
        // given:
        val cardDto = getPredefinedCardDto().apply { id = 3L }
        every { cardService.createCard(any<CardDto>()) } returns cardDto

        // when:
        mockMvc.perform(
            post("/cards")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cardDto))
        )
            // then:
            .andExpect(status().isCreated)
            .andExpect(header().string("Location", "/cards/3"))
    }

    @Test
    fun `should update card`() {
        // given:
        val cardDto = getPredefinedCardDto().apply { id = 4L }
        every { cardService.updateCard(any<CardDto>(), 4L) } returns cardDto

        // when:
        mockMvc.perform(
            put("/cards/4")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cardDto))
        )
            // then:
            .andExpect { status().isNoContent }
    }

    @Test
    fun `should delete card by id`() {
        // when:
        mockMvc.perform(delete("/cards/5"))
            // then:
            .andExpect { status().isNoContent }
    }
}