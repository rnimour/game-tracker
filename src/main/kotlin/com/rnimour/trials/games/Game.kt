package com.rnimour.trials.games

import com.rnimour.trials.games.PlayStatus.NOT_STARTED
import com.rnimour.trials.players.Player
import jakarta.persistence.*

// give a custom sequence with different initial value, because we initialize with some data (see data.sql)
private const val GAME_SEQ = "game_seq"

@Entity
data class Game(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = GAME_SEQ)
    @SequenceGenerator(name = GAME_SEQ, sequenceName = GAME_SEQ, allocationSize = 1, initialValue = 100)
    val id: Long? = null,
    @Column(nullable = false, unique = true)
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
    @ManyToMany(mappedBy = "gamesPlayed", cascade = [CascadeType.ALL])
    var playersPlaying: MutableList<Player> = mutableListOf(),
)

enum class PlayStatus(private val description: String) {
    NOT_STARTED("Not Started"),
    PLAYING("Playing"),
    COMPLETED("Completed");

    override fun toString() = description
}
