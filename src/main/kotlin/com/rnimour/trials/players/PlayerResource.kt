package com.rnimour.trials.players

import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.created
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.*
import java.net.URI

/**
 * Basic CRUD api for players.
 * Also allows for adding game to a player's gamesPlayed
 *
 * See also [PlayerExceptionHandler] which catches exceptions thrown by [PlayerService]
 */
@RestController
@RequestMapping("/api/players")
class PlayerResource(
    private val playerService: PlayerService,
) {
    @PostMapping
    fun createPlayer(@RequestBody playerRequest: PlayerDTOCreateRequest): ResponseEntity<Player> {
        val player = playerService.create(playerRequest)
        return created(URI("/api/players/${player.id}")).body(player)
    }

    @GetMapping
    fun getAllPlayers(): ResponseEntity<List<Player>> = ok(playerService.findAll())

    @GetMapping("/{id}")
    fun getPlayer(@PathVariable id: Long): ResponseEntity<Player> = ok(playerService.findByIdOrThrow(id))

    @PutMapping("/{id}")
    fun updatePlayer(
        @PathVariable id: Long,
        @RequestBody playerRequest: PlayerDTOUpdateRequest,
    ): ResponseEntity<Player> {
        val player = playerService.findByIdOrThrow(id)
        val updatedPlayer = playerService.updatePlayer(player, playerRequest)
        return ok(updatedPlayer)
    }

    @DeleteMapping("/{id}")
    fun deletePlayer(@PathVariable id: Long): ResponseEntity<Player> {
        val player = playerService.findByIdOrThrow(id)
        playerService.delete(player)
        return ok(player)
    }

    @PostMapping("/{id}/add-game")
    fun addPlayerToPlayer(
        @PathVariable id: Long,
        @RequestParam name: String,
    ): ResponseEntity<Player> {
        val player = playerService.addGameToPlayer(id, name)
        return ok(player)
    }
}