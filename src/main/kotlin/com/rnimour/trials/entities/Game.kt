package com.rnimour.trials.entities

import com.rnimour.trials.entities.PlayStatus.NOT_STARTED
import jakarta.persistence.*

@Entity
data class Game(
    @Column(nullable = false)
    var name: String,
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var playStatus: PlayStatus = NOT_STARTED,
    @Column(nullable = false)
    var releaseYear: Int,
    @Column(nullable = false)
    var developer: String,
    var series: String? = null,
    var genre: String? = null,
    @Id
    @GeneratedValue
    val id: Long? = null,
)

enum class PlayStatus(private val description: String) {
    NOT_STARTED("Not Started"),
    PLAYING("Playing"),
    COMPLETED("Completed");

    override fun toString() = description
}
