package com.example.todolistkotlin.features.user.repository

import com.example.todolistkotlin.features.user.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
}