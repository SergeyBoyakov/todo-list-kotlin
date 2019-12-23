package com.example.todolistkotlin.repository

import com.example.todolistkotlin.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
}