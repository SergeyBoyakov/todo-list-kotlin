package com.example.todolistkotlin.converter

import assertk.assertAll
import assertk.assertThat
import assertk.assertions.isEqualTo
import com.example.todolistkotlin.dto.CardDto
import com.example.todolistkotlin.model.Card
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class CardConverterTest {
    // todo  do something with nullable, if CardConverter?=null -> a lot of nullable checks
    private var converter: CardConverter = CardConverter()

    @BeforeEach
    fun initCardConverter() {
        converter = CardConverter()
    }

    @Test
    fun `should convert dto to entity`() {
        // given:
        val cardDto = CardDto().apply {
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
        val card = Card(title = "title", description = "desc").apply {
            id = 1L
        }

        // when:
        val dto = converter.toDto(card)

        // then:
        with(dto) {
            assertAll {
                assertAll {
                    assertThat(id).isEqualTo(1L)
                    assertThat(title).isEqualTo("title")
                    assertThat(description).isEqualTo("desc")
                }
            }
        }
    }

    @Test
    fun `should populate entity`() {
        // given:
        val cardEntity = Card(title = "oldTitle", description = "oldDesc").apply {
            id = 2L
        }
        val cardDto = CardDto().apply {
            title = "newTitle"
            description = "newDesc"
        }

        // when:
        converter.populateEntity(cardDto, cardEntity)

        // then:
        with(cardEntity) {
            assertAll {
                assertThat(id).isEqualTo(2L)
                assertThat(description).isEqualTo("newDesc")
                assertThat(title).isEqualTo("newTitle")
            }
        }
    }

    @Test
    fun `should convert collection of entities to dtos`() {
        // given:
        val firstCard = Card(title = "firstTitle", description = "firstDesc").apply {
            id = 1L
        }
        val secondCard = Card(title = "secondTitle", description = "secondDesc").apply {
            id = 2L
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
