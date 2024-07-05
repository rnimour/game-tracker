package com.rnimour.trials.games

import org.springframework.stereotype.Component

@Component
class DefaultGameService(private val gameRepository: GameRepository) : GameService {

    override fun create(gameRequest: GameDTOCreateRequest): Game {
        if (gameRepository.findByName(gameRequest.name) != null) {
            throw GameAlreadyExistsException(gameRequest.name)
        }
        val game = with(gameRequest) {
            Game(
                name = name,
                playStatus = playStatus,
                releaseYear = releaseYear,
                developer = developer,
                series = series,
                genre = genre,
            )
        }
        return gameRepository.save(game)
    }

    override fun transform(game: Game): GameDTOResponse = with(game) {
        GameDTOResponse(
            id = id ?: throw IllegalArgumentException("Game not persisted"),
            name = name,
            playStatus = playStatus,
            releaseYear = releaseYear,
            developer = developer,
            series = series,
            genre = genre,
        )
    }

    override fun updateGame(
        game: Game,
        gameRequest: GameDTOUpdateRequest,
    ): Game {
        with(game) {
            name = gameRequest.name ?: name
            playStatus = gameRequest.playStatus ?: playStatus
            releaseYear = gameRequest.releaseYear ?: releaseYear
            developer = gameRequest.developer ?: developer
            series = gameRequest.series ?: series
            genre = gameRequest.genre ?: genre

        }
        return gameRepository.save(game)
    }

    override fun findAll(): List<Game> = gameRepository.findAll()
    override fun deleteById(id: Long) = gameRepository.deleteById(id)
    override fun findById(id: Long): Game = gameRepository.findById(id).orElseThrow { GameNotFoundException(id) }
}