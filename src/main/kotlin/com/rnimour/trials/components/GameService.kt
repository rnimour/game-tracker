package com.rnimour.trials.components

import com.rnimour.trials.entities.Game
import com.rnimour.trials.entities.GameDTOCreateRequest
import com.rnimour.trials.entities.GameDTOResponse
import com.rnimour.trials.entities.GameDTOUpdateRequest

interface GameService {
    /**
     * Create a Game Entity from a GameDTORequest, and persist it
     */
    fun create(gameRequest: GameDTOCreateRequest): Game

    /**
     * Update (and persist) a Game Entity from a request
     */
    fun updateGame(game: Game, gameRequest: GameDTOUpdateRequest)

    /**
     * Transform a Game Entity to a GameDTOResponse
     */
    fun transform(game: Game): GameDTOResponse
}
