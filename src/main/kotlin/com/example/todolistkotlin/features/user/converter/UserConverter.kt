package com.example.todolistkotlin.features.user.converter

import com.example.todolistkotlin.features.user.dto.UserDto
import com.example.todolistkotlin.features.user.model.User
import org.springframework.stereotype.Service

@Service
class UserConverter {

    fun toDto(userEntity: User): UserDto {
        return UserDto().apply {
            id = userEntity.userId
            email = userEntity.email
            firstName = userEntity.firstName
            lastName = userEntity.lastName
        }
    }

    fun toEntity(userDto: UserDto): User {
        return User().apply {
            userId = userDto.id
            email = userDto.email
            firstName = userDto.firstName
            lastName = userDto.lastName
        }
    }

    fun toDtos(userEntities: List<User>): List<UserDto> = userEntities.map(this::toDto)

    fun populateEntity(userEntity: User, userDto: UserDto) {
        userEntity.email = userDto.email
        userEntity.firstName = userDto.firstName
        userEntity.lastName = userDto.lastName
    }

    fun populateEntity(existingUser: User, inputUser: User) {
        existingUser.email = inputUser.email
        existingUser.firstName = inputUser.firstName
        existingUser.lastName = inputUser.lastName
    }
}