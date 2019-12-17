package com.example.todolistkotlin.repository

import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

class CardRepository {
    @PersistenceContext
    private lateinit var entityManager: EntityManager
}