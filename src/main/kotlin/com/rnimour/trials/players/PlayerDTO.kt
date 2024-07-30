package com.rnimour.trials.players

import kotlinx.serialization.Serializable

@Serializable
data class PlayerDTOCreateRequest(
    var name: String,
    var favoriteGame: String? = null,
    var gamesPlayed: MutableList<String> = mutableListOf(),
)

@Serializable
data class PlayerDTOUpdateRequest(
    var name: String? = null,
    var favoriteGame: String? = null,
    var gamesPlayed: MutableList<String>? = null, // null to allow not changing this list
)

@Serializable
data class PlayerDTOResponse(
    var id: Long?,
    var name: String?,
    var favoriteGame: String?,
    var gamesPlayed: MutableList<String> = mutableListOf(),
)
