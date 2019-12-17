package com.example.todolistkotlin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class TodoListKotlinApplication

fun main(args: Array<String>) {
	runApplication<TodoListKotlinApplication>(*args)
}
