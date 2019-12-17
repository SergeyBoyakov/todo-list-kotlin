package com.example.todolistkotlin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TodoListKotlinApplication

fun main(args: Array<String>) {
	runApplication<TodoListKotlinApplication>(*args)
}
