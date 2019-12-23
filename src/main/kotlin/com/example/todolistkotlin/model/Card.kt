package com.example.todolistkotlin.model

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

    @ManyToOne(
        cascade = [CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH],
        fetch = FetchType.EAGER
    )
    @JoinColumn(name = "user_id", nullable = false)
    var creator: User
) {

    constructor(card: Card) : this(card.title, card.description, card.creator)

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var cardId: Long? = null
}
