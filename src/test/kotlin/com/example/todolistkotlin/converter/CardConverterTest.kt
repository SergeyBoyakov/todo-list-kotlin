package com.example.todolistkotlin.converter

import assertk.assertAll
import assertk.assertThat
import assertk.assertions.isEqualTo
import com.example.todolistkotlin.dto.CardDto
import com.example.todolistkotlin.model.Card
import com.example.todolistkotlin.model.User
import com.example.todolistkotlin.utils.creator.getPredefinedUserDto
import com.example.todolistkotlin.utils.creator.getPredefinedUserEntity
import io.mockk.mockk
import org.junit.jupiter.api.Test

internal class CardConverterTest {
    private val userConverter = mockk<UserConverter>(relaxed = true)
    private val converter = CardConverter(userConverter)

    @Test
    fun `should convert dto to entity`() {
        // given:
        val cardDto = getPredefinedCardDto().apply {
            title = "title"
            description = "desc"
            creator = getPredefinedUserDto()
        }

        // when:
        val entity = converter.toEntity(cardDto)

        // then:
        with(entity) {
            assertAll {
                assertThat(title).isEqualTo("title")
                assertThat(description).isEqualTo("desc")
            }
        }
    }

    @Test
    fun `should convert entity to dto`() {
        // given:
        val card = getPredefinedCard().apply {
            cardId = 1L
            creator = getPredefinedUserEntity()
        }

        // when:
        val dto = converter.toDto(card)

        // then:
        with(dto) {
            assertAll {
                assertAll {
                    assertThat(id).isEqualTo(1L)
                    assertThat(title).isEqualTo("firstTitle")
                    assertThat(description).isEqualTo("firstDesc")
                }
            }
        }
    }

    @Test
    fun `should populate entity`() {
        // given:
        val cardEntity = getPredefinedCard().apply {
            cardId = 2L
        }
        val cardDto = getPredefinedCardDto().apply {
            title = "newTitle"
            description = "newDesc"
        }

        // when:
        converter.populateEntity(cardDto, cardEntity)

        // then:
        with(cardEntity) {
            assertAll {
                assertThat(cardId).isEqualTo(2L)
                assertThat(description).isEqualTo("newDesc")
                assertThat(title).isEqualTo("newTitle")
            }
        }
    }

    @Test
    fun `should convert collection of entities to dtos`() {
        // given:
        val firstCard = getPredefinedCard().apply {
            cardId = 1L
            title = "firstTitle"
            description = "firstDesc"
        }
        val secondCard = getPredefinedCard().apply {
            cardId = 2L
            title = "secondTitle"
            description = "secondDesc"
        }

        // when:
        val cardDtos = converter.toDtos(listOf(firstCard, secondCard))

        // then:
        with(cardDtos) {
            assertAll {
                assertThat(size).isEqualTo(2)

                with(first()) {
                    assertThat(id).isEqualTo(1L)
                    assertThat(title).isEqualTo("firstTitle")
                    assertThat(description).isEqualTo("firstDesc")
                }

                with(last()) {
                    assertThat(id).isEqualTo(2L)
                    assertThat(title).isEqualTo("secondTitle")
                    assertThat(description).isEqualTo("secondDesc")
                }
            }
        }
    }

    private fun getPredefinedCard() = Card(title = "firstTitle", description = "firstDesc", creator = User())

    private fun getPredefinedCardDto() = CardDto().apply {
        title = "dto title"
        description = "dto desc"
        creator = getPredefinedUserDto()
    }
}
