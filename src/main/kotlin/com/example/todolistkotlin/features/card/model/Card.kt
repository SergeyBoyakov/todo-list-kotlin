package com.example.todolistkotlin.features.card.model

import com.example.todolistkotlin.features.user.model.User
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import javax.persistence.*


@Entity
@Table(name = "cards")
@EntityListeners(AuditingEntityListener::class)
data class Card(
    @Column(name = "title")
    var title: String?,

    @Column(name = "description")
    var description: String?,

    var creator: String
) {

    constructor(card: Card) : this(card.title, card.description, card.creator)

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var cardId: Long? = null
}
