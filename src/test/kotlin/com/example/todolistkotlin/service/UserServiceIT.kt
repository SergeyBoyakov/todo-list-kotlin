package com.example.todolistkotlin.service

import assertk.assertAll
import assertk.assertThat
import assertk.assertions.*
import com.example.todolistkotlin.MicroserviceIsolatedTest
import com.example.todolistkotlin.dto.UserDto
import com.example.todolistkotlin.model.User
import com.example.todolistkotlin.repository.UserRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

internal class UserServiceIT : MicroserviceIsolatedTest() {

    @Autowired
    private lateinit var service: UserService
    @Autowired
    private lateinit var repository: UserRepository

    @BeforeEach
    @AfterEach
    fun cleanupDb() {
        repository.deleteAllUsers()
    }

    @Test
    fun `should find all users`() {
        // given:
        val firstUser = fullySetUserEntity()
        val secondUser = fullySetUserEntity().apply {
            email = "ramsaybolton@gmail.com"
            firstName = "Ramsay"
            lastName = "Bolton"
        }
        repository.saveAll(listOf(firstUser, secondUser))

        // when:
        val foundUsers = service.findAllUsers()

        // then:
        with(foundUsers) {
            assertAll {
                assertThat(size).isEqualTo(2)

                with(first()) {
                    assertThat(id).isNotNull()
                    assertThat(email).isEqualTo("johnsnow@gmail.com")
                    assertThat(firstName).isEqualTo("John")
                    assertThat(lastName).isEqualTo("Snow")
                }

                with(last()) {
                    assertThat(id).isNotNull()
                    assertThat(email).isEqualTo("ramsaybolton@gmail.com")
                    assertThat(firstName).isEqualTo("Ramsay")
                    assertThat(lastName).isEqualTo("Bolton")
                }
            }
        }
    }

    @Test
    fun `should find user by id`() {
        // given:
        val fullySetUserEntity = fullySetUserEntity()
        val userId = repository.saveUser(fullySetUserEntity).userId!!

        // when:
        val userDto = service.findUserById(userId)

        // then:
        with(userDto) {
            assertAll {
                assertThat(email).isEqualTo(fullySetUserEntity.email)
                assertThat(firstName).isEqualTo(fullySetUserEntity.firstName)
                assertThat(lastName).isEqualTo(fullySetUserEntity.lastName)
            }
        }
    }

    @Test
    fun `should create new user`() {
        // given:
        val inputUserData = fullySetUserDto()

        // when:
        val createdUser = service.createUser(inputUserData)

        // then:
        assertAll {
            assertThat(createdUser.email).isEqualTo("geohot@gmail.com")
            assertThat(createdUser.firstName).isEqualTo("George")
            assertThat(createdUser.lastName).isEqualTo("Hotz")
        }
    }

    @Test
    fun `should update existing user`() {
        // given:
        val fullySetUserEntity = fullySetUserEntity()
        val existingUserId = givenExistingUser(fullySetUserEntity).userId!!
        val inputUserData = fullySetUserDto()
        assertAll {
            assertThat(fullySetUserEntity.email).isNotEqualTo(inputUserData.email)
            assertThat(fullySetUserEntity.firstName).isNotEqualTo(inputUserData.firstName)
            assertThat(fullySetUserEntity.lastName).isNotEqualTo(inputUserData.lastName)
        }

        // when:
        val updatedUser = service.updateUser(inputUserData, existingUserId)

        // then:
        assertAll {
            assertThat(updatedUser.email).isEqualTo(inputUserData.email)
            assertThat(updatedUser.firstName).isEqualTo(inputUserData.firstName)
            assertThat(updatedUser.lastName).isEqualTo(inputUserData.lastName)
        }
    }

    @Test
    fun `should delete user by user id`() {
        // given:
        val userForDeletionId = givenExistingUser(fullySetUserEntity()).userId!!

        // when:
        service.deleteUserById(userForDeletionId)

        // then:
        assertThat {
            repository.findUserById(userForDeletionId)
        }.isFailure().hasMessage("User with id: $userForDeletionId not found")
    }

    private fun fullySetUserDto() = UserDto().apply {
        email = "geohot@gmail.com"
        firstName = "George"
        lastName = "Hotz"
    }

    private fun fullySetUserEntity() = User().apply {
        email = "johnsnow@gmail.com"
        firstName = "John"
        lastName = "Snow"
    }

    private fun givenExistingUser(user: User) = repository.saveUser(user)
}