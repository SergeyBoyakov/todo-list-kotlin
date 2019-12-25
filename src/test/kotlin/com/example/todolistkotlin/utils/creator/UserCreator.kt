package com.example.todolistkotlin.utils.creator

import com.example.todolistkotlin.features.user.dto.UserDto
import com.example.todolistkotlin.features.user.model.User

fun getPredefinedUserDto() = UserDto().apply {
    firstName = "first name dto"
    lastName = "last name dto"
    email = "userdto@gmail.com"
}

fun getPredefinedUserEntity() = User().apply {
    firstName = "first name entity"
    lastName = "last name entity"
    email = "userentity@gmail.com"
}



