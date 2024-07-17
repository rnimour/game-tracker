package com.rnimour.trials.games

import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.created
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("/api/games")
class GameResource(
    val gameService: GameService,
) {

    @PostMapping
    fun createGame(@RequestBody gameRequest: GameDTOCreateRequest): ResponseEntity<Game> {
        val game = gameService.create(gameRequest)
        return created(URI("/api/games/${game.id}")).body(game)
    }

    @GetMapping
    fun getAllGames(): ResponseEntity<List<Game>> = ok(gameService.findAll())

    @GetMapping("/{id}")
    fun getGame(@PathVariable id: Long): ResponseEntity<Game> = ok(gameService.findById(id))

    @PatchMapping("/{id}")
    fun updateGame(
        @PathVariable id: Long,
        @RequestBody gameRequest: GameDTOUpdateRequest,
    ): ResponseEntity<Game> {
        val game = gameService.findById(id)
        val updatedGame = gameService.updateGame(game, gameRequest)
        return ok(updatedGame)
    }

    @DeleteMapping("/{id}")
    fun deleteGame(@PathVariable id: Long): ResponseEntity<Game> {
        val game = gameService.findById(id)
        gameService.deleteById(id)
        return ok(game)
    }
}
