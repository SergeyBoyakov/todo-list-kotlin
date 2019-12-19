package com.example.todolistkotlin.repository

import com.example.todolistkotlin.exception.CardNotFoundException
import com.example.todolistkotlin.model.Card
import org.springframework.stereotype.Service
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Service
class CardRepository {
    // container managed entity manager
    @PersistenceContext
    private lateinit var entityManager: EntityManager

    fun createEmptyCard(): Card {
        // todo no-arg plugin doesn't work this way?
        val card = Card(title = "", description = "")

        entityManager.run {
            transaction.begin()
            persist(card)
            transaction.commit()
        }

        return card
    }

    fun createCard(card: Card): Card {
        val createdCard = Card(card)

        return saveCard(createdCard)
    }

    fun saveCard(card: Card): Card {
        with(entityManager) {
            transaction.begin()
            persist(card)
            transaction.commit()
        }

        return card
    }

    fun findAllCards(): List<Card> {
        entityManager.transaction.begin()
        val query = entityManager.createQuery("SELECT e FROM cards e")

        val resultList = query.resultList
        entityManager.transaction.commit()

        return resultList as List<Card>
    }

    open fun findCardById(id: Long): Card {
        entityManager.transaction.begin()
        val card = entityManager.find(Card::class.java, id)
            ?: throw CardNotFoundException("Card with id: $id not found")
        entityManager.transaction.commit()

        return card
    }

    open fun deleteCardById(id: Long) {
        entityManager.transaction.begin()

        val card = findCardById(id)
        entityManager.remove(card)

        entityManager.transaction.commit()
    }
}