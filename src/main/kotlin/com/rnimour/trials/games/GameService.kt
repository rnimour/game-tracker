package com.rnimour.trials.games

interface GameService {
    /**
     * Create a Game Entity from a GameDTORequest, and persist it
     * @throws GameAlreadyExistsException if a game with the same name already exists
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

    /**
     * Get all games
     */
    fun findAll(): List<Game>

    /**
     * Find a game by its id
     * @throws GameNotFoundException if not found
     */
    fun findById(id: Long): Game

    /**
     * Delete a game by its id
     * @throws GameNotFoundException if not found
     */
    fun deleteById(id: Long)
}
