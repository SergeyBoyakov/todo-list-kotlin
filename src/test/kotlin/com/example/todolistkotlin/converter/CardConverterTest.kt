package com.example.todolistkotlin.converter

import assertk.assertAll
import assertk.assertThat
import assertk.assertions.isEqualTo
import com.example.todolistkotlin.dto.CardDto
import com.example.todolistkotlin.model.Card
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

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
            createdDate = Date(1111, 11, 22)
            updatedDate = Date(2222, 12, 11)
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
                    assertThat(createdAt).isEqualTo(Date(1111, 11, 22))
                    assertThat(updatedAt).isEqualTo(Date(2222, 12, 11))
                }
            }
        }
    }

    @Test
    fun `should populate entity`() {
        // given:
        val cardEntity = Card(title = "oldTitle", description = "oldDesc").apply {
            id = 2L
            createdDate = Date(1, 2, 3)
            updatedDate = Date(4, 5, 6)
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
                assertThat(createdDate).isEqualTo(Date(1, 2, 3))
                assertThat(updatedDate).isEqualTo(Date(4, 5, 6))
            }
        }
    }

    @Test
    fun `should convert collection of entities to dtos`() {
        // given:
        val firstCard = Card(title = "firstTitle", description = "firstDesc").apply {
            id = 1L
            createdDate = Date(1, 2, 3)
            updatedDate = Date(11, 12, 13)
        }
        val secondCard = Card(title = "secondTitle", description = "secondDesc").apply {
            id = 2L
            createdDate = Date(5, 6, 7)
            updatedDate = Date(7, 8, 9)
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
                    assertThat(createdAt).isEqualTo(Date(1, 2, 3))
                    assertThat(updatedAt).isEqualTo(Date(11, 12, 13))
                }

                with(last()) {
                    assertThat(id).isEqualTo(2L)
                    assertThat(title).isEqualTo("secondTitle")
                    assertThat(description).isEqualTo("secondDesc")
                    assertThat(createdAt).isEqualTo(Date(5, 6, 7))
                    assertThat(updatedAt).isEqualTo(Date(7, 8, 9))
                }
            }
        }
    }
}
