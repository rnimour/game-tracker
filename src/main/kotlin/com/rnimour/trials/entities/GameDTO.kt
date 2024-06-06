package com.rnimour.trials.entities

import com.fasterxml.jackson.annotation.JsonProperty

data class GameDTOCreateRequest(
    @JsonProperty("name")
    var name: String,
    @JsonProperty("playStatus")
    var playStatus: PlayStatus,
    @JsonProperty("releaseYear")
    var releaseYear: Int,
    @JsonProperty("developer")
    var developer: String,
    @JsonProperty("series")
    var series: String?,
    @JsonProperty("genre")
    var genre: String?,
)

data class GameDTOUpdateRequest(
    @JsonProperty("name")
    var name: String?,
    @JsonProperty("playStatus")
    var playStatus: PlayStatus?,
    @JsonProperty("releaseYear")
    var releaseYear: Int?,
    @JsonProperty("developer")
    var developer: String?,
    @JsonProperty("series")
    var series: String?,
    @JsonProperty("genre")
    var genre: String?,
)

data class GameDTOResponse(
    @JsonProperty("id")
    var id: Long?,
    @JsonProperty("name")
    var name: String?,
    @JsonProperty("playStatus")
    var playStatus: PlayStatus?,
    @JsonProperty("releaseYear")
    var releaseYear: Int?,
    @JsonProperty("developer")
    var developer: String?,
    @JsonProperty("series")
    var series: String?,
    @JsonProperty("genre")
    var genre: String?,
)
