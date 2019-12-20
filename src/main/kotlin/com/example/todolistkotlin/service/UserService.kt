package com.example.todolistkotlin.service

import com.example.todolistkotlin.converter.UserConverter
import com.example.todolistkotlin.dto.UserDto
import com.example.todolistkotlin.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val userConverter: UserConverter
) {
    fun findAllUsers(): List<UserDto> {
        val allUsers = userRepository.findAllUsers()

        return userConverter.toDtos(allUsers)
    }

    fun findUserById(userId: Long): UserDto {
        val user = userRepository.findUserById(userId)

        return userConverter.toDto(user)
    }

    fun createUser(user: UserDto): UserDto {
        val inputUserData = userConverter.toEntity(user)
        val createdUser = userRepository.createUser(inputUserData)

        return userConverter.toDto(createdUser)
    }

    fun updateUser(user: UserDto, userId: Long): UserDto {
        val inputUserData = userConverter.toEntity(user)
        val updatedEntity = userRepository.updateUser(inputUserData, userId)

        return userConverter.toDto(updatedEntity)
    }

    fun deleteUserById(userId: Long) {
        userRepository.deleteUserById(userId)
    }
}