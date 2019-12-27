package com.example.todolistkotlin.features.user.controller

import com.example.todolistkotlin.features.user.dto.UserDto
import com.example.todolistkotlin.features.user.service.UserService
import com.example.todolistkotlin.utils.creator.getPredefinedUserDto
import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(controllers = [UserController::class])
internal class UserControllerIT {
    @MockkBean(relaxed = true)
    private lateinit var userService: UserService

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Test
    fun `should find all users`() {
        // given:
        every { userService.findAllUsers() } returns listOf(
            getPredefinedUserDto().apply { id = 1L },
            getPredefinedUserDto().apply { id = 2L })

        // when:
        mockMvc.perform(get("/users").contentType(MediaType.APPLICATION_JSON))
            // then:
            .andExpect(status().isOk)
    }

    @Test
    fun `should find user by id`() {
        // given:
        every { userService.findUserById(1L) } returns getPredefinedUserDto().apply { id = 1L }

        // when:
        mockMvc.perform(get("/users/1").contentType(MediaType.APPLICATION_JSON))
            // then:
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(1L))
    }

    @Test
    fun `should create user`() {
        // given:
        every { userService.createUser(any<UserDto>()) } returns getPredefinedUserDto().apply { id = 3L }

        // when:
        mockMvc.perform(
            post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(getPredefinedUserDto()))
        )
            // then:
            .andExpect(status().isCreated)
            .andExpect(header().string("Location", "/users/3"))
    }

    @Test
    fun `should update user`() {
        // given:
        every { userService.updateUser(any<UserDto>(), 4L) } returns getPredefinedUserDto().apply { id = 4L }

        // when:
        mockMvc.perform(
            put("/users/4")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(getPredefinedUserDto()))
        )
            // then:
            .andExpect(status().isNoContent)
    }

    @Test
    fun `should delete user`() {
        // when:
        mockMvc.perform(delete("/users/5").contentType(MediaType.APPLICATION_JSON))
            // then:
            .andExpect(status().isNoContent)
    }
}