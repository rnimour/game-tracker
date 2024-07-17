package com.rnimour.trials.games

// jackson-module-kotlin automatically infers JSON property names for these DTO objects

data class GameDTOCreateRequest(
    var name: String,
    var playStatus: PlayStatus,
    var releaseYear: Int,
    var developer: String,
    var series: String?,
    var genre: String?,
)

data class GameDTOUpdateRequest(
    var name: String? = null,
    var playStatus: PlayStatus? = null,
    var releaseYear: Int? = null,
    var developer: String? = null,
    var series: String? = null,
    var genre: String? = null,
)

data class GameDTOResponse(
    var id: Long?,
    var name: String?,
    var playStatus: PlayStatus?,
    var releaseYear: Int?,
    var developer: String?,
    var series: String?,
    var genre: String?,
)
