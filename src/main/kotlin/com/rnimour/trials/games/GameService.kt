package com.rnimour.trials.games

interface GameService {
    /**
     * Create a [Game] from a [GameDTOCreateRequest], and persist it
     * @throws GameAlreadyExistsException if a game with the same name already exists
     */
    fun create(gameRequest: GameDTOCreateRequest): Game

    /**
     * Get all games
     * TODO: add pagination/prevent loading too many games
     */
    fun findAll(): List<Game>

    /**
     * Find a game by its id
     * @throws GameNotFoundException if not found
     */
    fun findByIdOrThrow(id: Long): Game

    /**
     * Update (and persist) a [Game] from a request
     */
    fun updateGame(game: Game, gameRequest: GameDTOUpdateRequest): Game

    /**
     * Transform a [Game] to a [GameDTOResponse]
     */
    fun transform(game: Game): GameDTOResponse

    /**
     * Find a [Game] by its [name]
     * @throws GameNotFoundException if not found
     */
    fun findByNameOrThrow(name: String): Game

    /**
     * Delete a [Game]
     */
    fun delete(game: Game)
}
