package com.rnimour.trials.players

import com.rnimour.trials.games.Game
import jakarta.persistence.*
import kotlinx.serialization.Serializable


@Entity
@Serializable
class Player(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    val id: Long? = null,
    @Column(nullable = false, unique = true)
    var name: String,
    @ManyToOne
    var favoriteGame: Game?,
    @ManyToMany(cascade = [CascadeType.ALL])
    @JoinTable(
        name = "player_games",
        joinColumns = [JoinColumn(name = "player_id")],
        inverseJoinColumns = [JoinColumn(name = "game_id")],
    )
    var gamesPlayed: MutableList<Game> = mutableListOf(),
) {
    // override fun toString(): String {
    //     return "Player(id=$id, name='$name', favoriteGame=$favoriteGame, gamesPlayed=$gamesPlayed)"
    // }
}