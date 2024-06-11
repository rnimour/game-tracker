package com.rnimour.trials.games

interface GameService {
    /**
     * Create a Game Entity from a GameDTORequest, and persist it
     */
    fun create(gameRequest: GameDTOCreateRequest): Game

    /**
     * Update (and persist) a Game Entity from a request
     */
    fun updateGame(game: Game, gameRequest: GameDTOUpdateRequest): Game

    /**
     * Transform a Game Entity to a GameDTOResponse
     */
    fun transform(game: Game): GameDTOResponse
}
