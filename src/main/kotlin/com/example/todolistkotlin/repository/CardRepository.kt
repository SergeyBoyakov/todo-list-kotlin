package com.example.todolistkotlin.repository

import com.example.todolistkotlin.exception.CardNotFoundException
import com.example.todolistkotlin.model.Card
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.transaction.Transactional

@Repository
@Transactional
class CardRepository {
    // container managed entity manager
    @PersistenceContext
    private lateinit var entityManager: EntityManager

    fun createEmptyCard(): Card {
        // todo no-arg plugin doesn't work this way?
        val card = Card(title = "", description = "")

        entityManager.run {
            persist(card)
        }

        return card
    }

    fun createCard(card: Card): Card {
        val createdCard = Card(card)

        return saveCard(createdCard)
    }

    fun saveCard(card: Card): Card {
        with(entityManager) {
            persist(card)
        }

        return card
    }

    fun findAllCards(): List<Card> {
        return entityManager.createQuery("SELECT c FROM Card c", Card::class.java).resultList
    }

    open fun findCardById(id: Long): Card {
        return entityManager.find(Card::class.java, id)
            ?: throw CardNotFoundException("Card with id: $id not found")
    }

    open fun deleteCardById(id: Long) {
        val card = findCardById(id)
        entityManager.remove(card)
    }

    open fun deleteAll() {
        val query = entityManager.createQuery("DELETE FROM Card")
        query.executeUpdate()
    }
}