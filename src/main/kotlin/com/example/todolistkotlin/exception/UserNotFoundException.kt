package com.example.todolistkotlin.exception

import java.lang.RuntimeException

class UserNotFoundException(userId: Long) : RuntimeException("User with id: $userId not found")


