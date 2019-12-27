package com.example.todolistkotlin.features.user.controller

import com.example.todolistkotlin.features.user.dto.UserDto
import com.example.todolistkotlin.features.user.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("/users")
class UserController(private val userService: UserService) {

    @GetMapping
    fun findAllUsers(): ResponseEntity<List<UserDto>> = ResponseEntity.ok(userService.findAllUsers())

    @GetMapping("/{userId}")
    fun findUserById(@PathVariable(value = "userId") userId: Long): ResponseEntity<UserDto> =
        ResponseEntity.ok(userService.findUserById(userId))

    @PostMapping
    fun createUser(@RequestBody user: UserDto): ResponseEntity<UserDto> {
        val createdUserId = userService.createUser(user).id

        return ResponseEntity.created(URI.create("/users/$createdUserId")).build()
    }

    @PutMapping("/{userId}")
    fun updateUser(@RequestBody user: UserDto, @PathVariable(value = "userId") userId: Long): ResponseEntity<UserDto> {
        val updatedUser = userService.updateUser(user, userId)

        return ResponseEntity.noContent().build()
    }

    @DeleteMapping("/{userId}")
    fun deleteUserById(@PathVariable(value = "userId") userId: Long): ResponseEntity<Any> {
        userService.deleteUserById(userId)

        return ResponseEntity.noContent().build()
    }
}