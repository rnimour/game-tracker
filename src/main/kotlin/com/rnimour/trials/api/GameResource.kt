package com.rnimour.trials.api

import com.rnimour.trials.components.GameRepository
import com.rnimour.trials.components.GameService
import com.rnimour.trials.entities.Game
import com.rnimour.trials.entities.GameDTOCreateRequest
import com.rnimour.trials.entities.GameDTOUpdateRequest
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.created
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("/api/games")
class GameResource(
    val gameRepository: GameRepository,
    val gameService: GameService,
) {

    @PostMapping
    fun createGame(@RequestBody gameRequest: GameDTOCreateRequest): ResponseEntity<Game> {
        val game = gameService.create(gameRequest)
        return created(URI("/api/games/${game.id}")).body(game)
    }

    @GetMapping
    fun getAllGames(): List<Game> {
        return gameRepository.findAll()
    }

    @GetMapping("/{id}")
    fun getGame(@PathVariable id: Long): ResponseEntity<Game> {
        val game = resolveGame(id)
        return ok(game)
    }

    @PatchMapping("/{id}")
    fun updateGame(
        @PathVariable id: Long,
        @RequestBody gameRequest: GameDTOUpdateRequest,
    ): ResponseEntity<Game> {
        val game = resolveGame(id)
        return ok(gameService.updateGame(game, gameRequest))
    }

    @DeleteMapping("/{id}")
    fun deleteGame(@PathVariable id: Long): ResponseEntity<Game> {
        val game = resolveGame(id)
        gameRepository.deleteById(id)
        return ok(game)
    }

    private fun resolveGame(id: Long): Game = gameRepository.findById(id).orElseThrow { GameNotFoundException(id) }
}