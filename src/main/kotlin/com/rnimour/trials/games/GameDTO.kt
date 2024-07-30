package com.rnimour.trials.games

import kotlinx.serialization.Serializable

@Serializable
data class GameDTOCreateRequest(
    var name: String,
    var playStatus: PlayStatus,
    var releaseYear: Int,
    var developer: String,
    var series: String?,
    var genre: String?,
    var playersPlaying: MutableList<String>? = mutableListOf(),
)

@Serializable
data class GameDTOUpdateRequest(
    var name: String? = null,
    var playStatus: PlayStatus? = null,
    var releaseYear: Int? = null,
    var developer: String? = null,
    var series: String? = null,
    var genre: String? = null,
    var playersPlaying: MutableList<String>? = null,
)

@Serializable
data class GameDTOResponse(
    var id: Long?,
    var name: String?,
    var playStatus: PlayStatus?,
    var releaseYear: Int?,
    var developer: String?,
    var series: String?,
    var genre: String?,
    var playersPlaying: MutableList<String>? = null,
)
