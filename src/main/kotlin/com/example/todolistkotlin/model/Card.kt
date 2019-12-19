package com.example.todolistkotlin.model

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.util.*
import javax.persistence.*


@Entity
@Table(name = "cards")
@EntityListeners(AuditingEntityListener::class)
data class Card(
    @Column(name = "title")
    var title: String?,

    @Column(name = "description")
    var description: String?
) {

    constructor(card: Card) : this(card.title, card.description)

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null

    // todo do something with date class, its depricated
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    lateinit var createdDate: Date


    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    lateinit var updatedDate: Date
}
