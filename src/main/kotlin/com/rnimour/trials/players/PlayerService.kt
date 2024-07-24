package com.rnimour.trials.players

import com.rnimour.trials.games.GameAlreadyPlayingException
import com.rnimour.trials.games.GameNotFoundException

interface PlayerService {

    /**
     * Create a Player Entity from a PlayerDTORequest, and persist it
     * @throws PlayerAlreadyExistsException if a player with the same name already exists
     */
    fun create(playerRequest: PlayerDTOCreateRequest): Player

    /**
     * Get all players
     * TODO: add pagination/prevent loading too many players
     */
    fun findAll(): List<Player>

    /**
     * Find a [Player] by its id
     * @throws PlayerNotFoundException if not found
     */
    fun findByIdOrThrow(id: Long): Player

    /**
     * Update (and persist) a [Player] from a request
     */
    fun updatePlayer(player: Player, playerRequest: PlayerDTOUpdateRequest): Player

    /**
     * Transform a [Player] to a [PlayerDTOResponse]
     */
    fun transform(player: Player): PlayerDTOResponse

    /**
     * Delete a [Player]
     */
    fun delete(player: Player)

    /**
     * Add a game to a player's gamesPlayed.
     * @throws PlayerNotFoundException if the player is not found
     * @throws GameNotFoundException if the game is not found
     * @throws GameAlreadyPlayingException if the game is already in the player's gamesPlayed
     * @return the player with the added game
     */
    fun addGameToPlayer(playerId: Long, gameName: String): Player
}
