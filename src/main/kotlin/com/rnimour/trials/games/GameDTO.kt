package com.rnimour.trials.games

import com.fasterxml.jackson.annotation.JsonProperty

data class GameDTOCreateRequest(
    @JsonProperty var name: String,
    @JsonProperty var playStatus: PlayStatus,
    @JsonProperty var releaseYear: Int,
    @JsonProperty var developer: String,
    @JsonProperty var series: String?,
    @JsonProperty var genre: String?,
)

data class GameDTOUpdateRequest(
    @JsonProperty var name: String? = null,
    @JsonProperty var playStatus: PlayStatus? = null,
    @JsonProperty var releaseYear: Int? = null,
    @JsonProperty var developer: String? = null,
    @JsonProperty var series: String? = null,
    @JsonProperty var genre: String? = null,
)

data class GameDTOResponse(
    @JsonProperty var id: Long?,
    @JsonProperty var name: String?,
    @JsonProperty var playStatus: PlayStatus?,
    @JsonProperty var releaseYear: Int?,
    @JsonProperty var developer: String?,
    @JsonProperty var series: String?,
    @JsonProperty var genre: String?,
)
