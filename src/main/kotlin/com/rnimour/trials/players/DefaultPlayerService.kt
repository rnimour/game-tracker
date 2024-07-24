package com.rnimour.trials.players

import com.rnimour.trials.games.GameAlreadyPlayingException
import com.rnimour.trials.games.GameService
import org.springframework.stereotype.Component

@Component
class DefaultPlayerService(
    private val playerRepository: PlayerRepository,
    private val gameService: GameService,
) : PlayerService {

    override fun create(playerRequest: PlayerDTOCreateRequest): Player {
        if (playerRepository.findByName(playerRequest.name) != null) {
            throw PlayerAlreadyExistsException(playerRequest.name)
        }
        val player = with(playerRequest) {
            Player(
                name = name,
                favoriteGame = favoriteGame,
                gamesPlayed = gamesPlayed,
            )
        }
        return playerRepository.save(player)
    }

    override fun updatePlayer(
        player: Player,
        playerRequest: PlayerDTOUpdateRequest,
    ): Player {
        with(player) {
            name = playerRequest.name ?: name
            favoriteGame = playerRequest.favoriteGame ?: favoriteGame
            gamesPlayed = playerRequest.gamesPlayed ?: gamesPlayed
        }
        return playerRepository.save(player)
    }

    override fun transform(player: Player): PlayerDTOResponse = with(player) {
        PlayerDTOResponse(
            id = id ?: throw IllegalArgumentException("Player not persisted"),
            name = name,
            favoriteGame = favoriteGame,
            gamesPlayed = gamesPlayed,
        )
    }

    override fun findAll(): List<Player> = playerRepository.findAll()
    override fun findByIdOrThrow(id: Long): Player = playerRepository.findById(id).orElseThrow { PlayerNotFoundException(id) }
    override fun delete(player: Player) = playerRepository.delete(player)

    override fun addGameToPlayer(playerId: Long, gameName: String): Player {
        val player = findByIdOrThrow(playerId)
        val game = gameService.findByNameOrThrow(gameName)
        if (!player.gamesPlayed.add(game))
            throw GameAlreadyPlayingException(player.name, game.name)
        return playerRepository.save(player)
    }
}