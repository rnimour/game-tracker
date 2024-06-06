package com.rnimour.trials.api

import com.rnimour.trials.components.GameRepository
import com.rnimour.trials.components.GameService
import com.rnimour.trials.entities.GameDTOCreateRequest
import com.rnimour.trials.entities.GameDTOResponse
import com.rnimour.trials.entities.GameDTOUpdateRequest
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/games")
class GameResource(
    val gameRepository: GameRepository,
    val gameService: GameService,
) {

    @PostMapping
    fun createGame(@RequestBody gameRequest: GameDTOCreateRequest): GameDTOResponse {
        val game = gameService.create(gameRequest)
        return gameService.transform(game)
    }

    @GetMapping
    fun getAllGames(): List<GameDTOResponse> {
        return gameRepository.findAll().map { gameService.transform(it) }
    }

    @GetMapping("/{id}")
    fun getGame(@PathVariable id: Long): GameDTOResponse? {
        val game = gameRepository.findById(id).orElse(null) ?: return null // TODO return 404 if game not found
        return gameService.transform(game)
    }

    @PatchMapping("/{id}")
    fun updateGame(
        @PathVariable id: Long,
        @RequestBody gameRequest: GameDTOUpdateRequest,
    ): GameDTOResponse? {
        val game = gameRepository.findById(id).orElse(null) ?: return null // TODO return 404 if game not found
        gameService.updateGame(game, gameRequest)
        return gameService.transform(game)
    }

    @DeleteMapping("/{id}")
    fun deleteGame(@PathVariable id: Long): GameDTOResponse? {
        val game = gameRepository.findById(id).orElse(null) ?: return null // TODO return 404 if game not found
        gameRepository.deleteById(id)
        return gameService.transform(game)
    }
}