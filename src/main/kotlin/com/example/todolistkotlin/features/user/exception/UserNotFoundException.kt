package com.example.todolistkotlin.features.user.exception

import java.lang.RuntimeException

class UserNotFoundException(userId: Long) : RuntimeException("User with id: $userId not found")


