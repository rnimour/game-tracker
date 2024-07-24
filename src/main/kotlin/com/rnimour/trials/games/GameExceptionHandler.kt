package com.rnimour.trials.games

import org.springframework.http.HttpStatus.*
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.zalando.problem.Problem
import org.zalando.problem.Status

class GameAlreadyExistsException(name: String) : RuntimeException("Game with name $name already exists")
class GameNotFoundException : RuntimeException {
    constructor(id: Long) : super("Game with id $id not found")
    constructor(name: String) : super("Game with name $name not found")
}
class GameAlreadyPlayingException(playerName: String, gameName: String) :
    RuntimeException("Player $playerName is already playing game $gameName")

@ControllerAdvice
class GameExceptionHandler {

    @ExceptionHandler(GameNotFoundException::class)
    fun handleGameNotFound(ex: GameNotFoundException): ResponseEntity<Any> = Problem.builder()
        .withTitle(ex.message ?: "Game not found")
        .withStatus(Status.NOT_FOUND)
        .build().let { ResponseEntity.status(NOT_FOUND).body(it) }


    @ExceptionHandler(GameAlreadyExistsException::class)
    fun handleGameAlreadyExists(ex: GameAlreadyExistsException): ResponseEntity<Any> = Problem.builder()
        .withTitle(ex.message ?: "Game already exists")
        .withStatus(Status.CONFLICT)
        .build().let { ResponseEntity.status(CONFLICT).body(it) }

    @ExceptionHandler(GameAlreadyPlayingException::class)
    fun handleGameAlreadyPlaying(ex: GameAlreadyPlayingException): ResponseEntity<Any> = Problem.builder()
        .withTitle(ex.message ?: "Player is already playing this game")
        .withStatus(Status.CONFLICT)
        .build().let { ResponseEntity.status(CONFLICT).body(it) }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleNotReadable(ex: HttpMessageNotReadableException): ResponseEntity<Any> = Problem.builder()
        .withTitle("Invalid JSON")
        .withDetail(ex.message)
        .build().let { ResponseEntity.status(BAD_REQUEST).body(it) }

    fun handeDefault(ex: Exception): ResponseEntity<Any> =
        Problem.builder()
            .withTitle("Unexpected Internal Server Error")
            .withDetail(ex.message ?: "Unknown: An unknown error occurred")
            .build().let { ResponseEntity.status(INTERNAL_SERVER_ERROR).body(it) }
}
