package com.rnimour.trials.players

import com.rnimour.trials.games.Game

// jackson-module-kotlin automatically infers JSON property names for these DTO objects

data class PlayerDTOCreateRequest(
    var name: String,
    var favoriteGame: Game? = null,
    var gamesPlayed: MutableList<Game> = mutableListOf(),
)

data class PlayerDTOUpdateRequest(
    var name: String? = null,
    var favoriteGame: Game? = null,
    var gamesPlayed: MutableList<Game> = mutableListOf(),
)

data class PlayerDTOResponse(
    var id: Long?,
    var name: String?,
    var favoriteGame: Game?,
    var gamesPlayed: MutableList<Game> = mutableListOf(),
)
