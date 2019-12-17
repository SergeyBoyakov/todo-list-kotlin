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
    val title: String,

    @Column(name = "description")
    val description: String
) {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null

    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    lateinit var createdDate: Date


    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    lateinit var updatedDate: Date
}
