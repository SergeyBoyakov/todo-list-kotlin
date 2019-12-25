package com.example.todolistkotlin.converter

import assertk.assertAll
import assertk.assertThat
import assertk.assertions.isEqualTo
import com.example.todolistkotlin.features.card.converter.CardConverter
import com.example.todolistkotlin.utils.creator.getPredefinedCardDto
import com.example.todolistkotlin.utils.creator.getPredefinedCardEntity
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class CardConverterTest {
    private lateinit var converter: CardConverter

    @BeforeEach
    fun init() {
        converter = CardConverter()
    }

    @Test
    fun `should convert dto to entity`() {
        // given:
        val cardDto = getPredefinedCardDto().apply {
            title = "title"
            description = "desc"
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
        val card = getPredefinedCardEntity().apply {
            cardId = 1L
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
        val cardEntity = getPredefinedCardEntity().apply {
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
        val firstCard = getPredefinedCardEntity().apply {
            cardId = 1L
            title = "firstTitle"
            description = "firstDesc"
        }
        val secondCard = getPredefinedCardEntity().apply {
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
}
