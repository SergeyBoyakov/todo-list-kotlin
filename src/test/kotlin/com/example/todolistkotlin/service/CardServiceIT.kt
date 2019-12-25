package com.example.todolistkotlin.service

import assertk.assertAll
import assertk.assertThat
import assertk.assertions.hasSize
import assertk.assertions.isEqualTo
import assertk.assertions.isNotNull
import com.example.todolistkotlin.MicroserviceIsolatedTest
import com.example.todolistkotlin.features.card.dto.CardDto
import com.example.todolistkotlin.features.card.exception.CardNotFoundException
import com.example.todolistkotlin.features.card.repository.CardRepository
import com.example.todolistkotlin.features.card.service.CardService
import com.example.todolistkotlin.features.user.repository.UserRepository
import com.example.todolistkotlin.utils.creator.getPredefinedCardDto
import com.example.todolistkotlin.utils.creator.getPredefinedCardEntity
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.util.*

open class CardServiceIT : MicroserviceIsolatedTest() {
    @Autowired
    lateinit var cardRepository: CardRepository

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var cardService: CardService

    @BeforeEach
    @AfterEach
    fun cleanupDb() {
        cardRepository.deleteAll()
        userRepository.deleteAll()
    }

    @Test
    fun `should get all cards from database`() {
        // given:
        val firstCard = getPredefinedCardEntity().apply {
            title = "firstTitle"
            description = "firstDescription"
        }
        val secondCard = getPredefinedCardEntity().apply {
            title = "secondTitle"
            description = "secondDescription"
        }

        cardRepository.saveAndFlush(firstCard)
        cardRepository.saveAndFlush(secondCard)

        // when:
        val foundCards = cardService.findAllCards()

        // then:
        assertThat(foundCards).hasSize(2)
        with(foundCards.first()) {
            assertThat(id).isNotNull()
            assertThat(title).isEqualTo("firstTitle")
            assertThat(description).isEqualTo("firstDescription")
        }
        with(foundCards.last()) {
            assertThat(id).isNotNull()
            assertThat(title).isEqualTo("secondTitle")
            assertThat(description).isEqualTo("secondDescription")
        }
    }

    @Test
    fun `should create new card`() {
        // given:
        val cardDto = getPredefinedCardDto().apply {
            title = "title"
            description = "desc"
        }

        // when:
        val createdCard = cardService.createCard(cardDto)

        // then:
        with(createdCard) {
            assertAll {
                assertThat(id).isNotNull()
                assertThat(title).isEqualTo("title")
                assertThat(description).isEqualTo("desc")
            }
        }
    }

    @Test
    fun `should update card by id`() {
        // given:
        val existingCard = cardRepository.saveAndFlush(getPredefinedCardEntity())
        with(existingCard) {
            assertThat(cardId).isNotNull()
            assertThat(title).isEqualTo("firstTitle")
            assertThat(description).isEqualTo("firstDescription")
        }

        val cardDto = CardDto().apply {
            title = "new title"
            description = "new desc"
        }

        // when:
        cardService.updateCard(cardDto, existingCard.cardId!!)

        // then:
        val updatedCard =
            cardRepository.findById(existingCard.cardId!!).orElseThrow { CardNotFoundException(existingCard.cardId!!) }
        with(updatedCard) {
            assertAll {
                assertThat(title).isEqualTo("new title")
                assertThat(description).isEqualTo("new desc")
            }
        }
    }

    @Test
    fun `should delete card by id`() {
        // given:
        val cardId = cardRepository.save(getPredefinedCardEntity()).cardId

        // when:
        cardService.deleteCardById(cardId!!)

        // then:
        assertThat(cardRepository.findById(cardId)).isEqualTo(Optional.empty())
    }
}
