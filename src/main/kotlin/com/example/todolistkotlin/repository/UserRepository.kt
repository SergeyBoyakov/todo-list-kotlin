package com.example.todolistkotlin.repository

import com.example.todolistkotlin.converter.UserConverter
import com.example.todolistkotlin.exception.UserNotFoundException
import com.example.todolistkotlin.model.User
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.transaction.Transactional

@Repository
@Transactional
class UserRepository(
    @PersistenceContext val entityManager: EntityManager,
    val userConverter: UserConverter
) {

    fun findAllUsers(): List<User> = entityManager.createQuery("SELECT u from User u", User::class.java).resultList

    fun findUserById(id: Long): User = entityManager.find(User::class.java, id)
        ?: throw UserNotFoundException("User with id: $id not found")


    fun updateUser(inputUser: User, userId: Long): User {
        val user = findUserById(userId)
        userConverter.populateEntity(user, inputUser)

        return saveUser(user)
    }

    fun createUser(user: User): User {
        val newUser = User(user)

        return saveUser(newUser)
    }

    fun saveUser(user: User): User {
        entityManager.persist(user)

        return user
    }

    fun saveAll(users: List<User>) {
        users.forEach { saveUser(it) }
    }

    fun deleteUserById(userId: Long) {
        val user = findUserById(userId)
        entityManager.remove(user)
    }

    fun deleteAllUsers() {
        val query = entityManager.createQuery("DELETE from User")
        query.executeUpdate()
    }
}
