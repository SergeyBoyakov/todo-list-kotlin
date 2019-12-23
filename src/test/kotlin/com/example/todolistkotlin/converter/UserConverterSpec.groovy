package com.example.todolistkotlin.converter

import com.example.todolistkotlin.dto.UserDto
import com.example.todolistkotlin.model.User
import spock.lang.Specification

class UserConverterSpec extends Specification {
    private UserConverter converter

    def setup() {
        converter = new UserConverter()
    }

    def 'should convert user entity to user dto'() {
        given:
        def userEntity = new User(
                userId: 1L,
                email: "cptjacksparrow@google.com",
                firstName: "Jack",
                lastName: "Sparrow"
        )

        when:
        def userDto = converter.toDto(userEntity)

        then:
        userDto.id == 1L
        userDto.email == "cptjacksparrow@google.com"
        userDto.firstName == "Jack"
        userDto.lastName == "Sparrow"
    }

    def 'should convert user dto to user entity'() {
        given:
        def userDto = new UserDto(
                id: 1L,
                email: "cptjacksparrow@google.com",
                firstName: "Jack",
                lastName: "Sparrow"
        )

        when:
        def userEntity = converter.toEntity(userDto)

        then:
        userEntity.userId == 1L
        userEntity.email == "cptjacksparrow@google.com"
        userEntity.firstName == "Jack"
        userEntity.lastName == "Sparrow"
    }

    def 'should convert list of entities to list of dtos'() {
        given:
        def firstUserEntity = new User(
                userId: 1L,
                email: "cptjacksparrow@google.com",
                firstName: "Jack",
                lastName: "Sparrow"
        )
        def secondUserEntity = new User(
                userId: 2L,
                email: "cptbarbossa@google.com",
                firstName: "Hector",
                lastName: "Barbossa"
        )

        when:
        def userDtos = converter.toDtos([firstUserEntity, secondUserEntity])

        then:
        userDtos.size() == 2
        with(userDtos.first()) {
            it.id == 1L
            it.email == "cptjacksparrow@google.com"
            it.firstName == "Jack"
            it.lastName == "Sparrow"
        }
        with(userDtos.last()) {
            it.id == 2L
            it.email == "cptbarbossa@google.com"
            it.firstName == "Hector"
            it.lastName == "Barbossa"
        }
    }

    def 'should populate entity from dto'() {
        given:
        def userEntity = new User(userId: 1L, email: "", firstName: "", lastName: "")
        def userDto = new UserDto(email: "geohot@comma.ai", firstName: "George", lastName: "Hotz")

        when:
        converter.populateEntity(userEntity, userDto)

        then:
        userEntity.userId == 1L
        userEntity.email == "geohot@comma.ai"
        userEntity.firstName == "George"
        userEntity.lastName == "Hotz"
    }

    def 'should populate existingEntity from inputEntity'() {
        given:
        def existingEntity = new User(userId: 1L, email: "", firstName: "", lastName: "")
        def inputEntity = new User(email: "geohot@comma.ai", firstName: "George", lastName: "Hotz")

        when:
        converter.populateEntity(existingEntity, inputEntity)

        then:
        existingEntity.userId == 1L
        existingEntity.email == "geohot@comma.ai"
        existingEntity.firstName == "George"
        existingEntity.lastName == "Hotz"
    }
}
