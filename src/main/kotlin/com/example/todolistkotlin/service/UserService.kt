package com.example.todolistkotlin.service

import com.example.todolistkotlin.converter.UserConverter
import com.example.todolistkotlin.dto.UserDto
import com.example.todolistkotlin.exception.UserNotFoundException
import com.example.todolistkotlin.model.User
import com.example.todolistkotlin.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserService(
    private val userRepository: UserRepository,
    private val userConverter: UserConverter
) {
    fun findAllUsers(): List<UserDto> {
        val allUsers = userRepository.findAll()

        return userConverter.toDtos(allUsers)
    }

    fun findUserById(userId: Long): UserDto {
        val user = userRepository.findById(userId)
            .orElseThrow { UserNotFoundException(userId) }

        return userConverter.toDto(user)
    }

    fun createUser(user: UserDto): UserDto {
        val inputUserData = userConverter.toEntity(user)
        val createdUser = userRepository.save(inputUserData)

        return userConverter.toDto(createdUser)
    }

    fun findUserEntityById(userId: Long): User = userRepository.findById(userId)
        .orElseThrow { UserNotFoundException(userId) }

    fun updateUser(user: UserDto, userId: Long): UserDto {
        val existingUser = this.findUserEntityById(userId)
        userConverter.populateEntity(userEntity = existingUser, userDto = user)

        return userConverter.toDto(existingUser)
    }

    fun deleteUserById(userId: Long) = userRepository.deleteById(userId)
}