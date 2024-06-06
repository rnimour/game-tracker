package com.rnimour.trials.components

import com.rnimour.trials.entities.Game
import com.rnimour.trials.entities.GameDTOCreateRequest
import com.rnimour.trials.entities.GameDTOResponse
import com.rnimour.trials.entities.GameDTOUpdateRequest
import org.springframework.stereotype.Component

@Component
class DefaultGameService(private val gameRepository: GameRepository) : GameService {

    override fun create(gameRequest: GameDTOCreateRequest): Game {
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

    override fun transform(game: Game) = with(game) {
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
    ) {
        with(game) {
            name = gameRequest.name ?: name
            playStatus = gameRequest.playStatus ?: playStatus
            releaseYear = gameRequest.releaseYear ?: releaseYear
            developer = gameRequest.developer ?: developer
            series = gameRequest.series ?: series
            genre = gameRequest.genre ?: genre

        }
        gameRepository.save(game)
    }
}