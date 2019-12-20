package com.example.todolistkotlin.model

import javax.persistence.*
import javax.validation.constraints.NotEmpty

@Entity
class User() {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null

    @Column(name = "email", nullable = false, unique = true)
    @NotEmpty
    lateinit var email: String

    @Column(name = "first_name")
    lateinit var firstName: String

    @Column(name = "last_name")
    lateinit var lastName: String

    constructor(user: User) : this() {
        this.email = user.email
        this.firstName = user.firstName
        this.lastName = user.lastName
    }
}