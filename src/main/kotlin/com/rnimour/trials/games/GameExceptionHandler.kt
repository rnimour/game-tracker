package com.rnimour.trials.games

import org.springframework.http.HttpStatus.*
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.zalando.problem.Problem

class GameAlreadyExistsException(name: String) : RuntimeException("Game with name $name already exists")
class GameNotFoundException(id: Long): RuntimeException("Game with id $id not found")

@ControllerAdvice
class GameExceptionHandler {

    @ExceptionHandler(GameNotFoundException::class)
    fun handleGameNotFound(ex: GameNotFoundException): ResponseEntity<Any> =
        ResponseEntity.status(NOT_FOUND).body(ex.message ?: "Unknown: game not found")

    @ExceptionHandler(GameAlreadyExistsException::class)
    fun handleGameAlreadyExists(ex: GameAlreadyExistsException): ResponseEntity<Any> =
        ResponseEntity.status(CONFLICT).body(ex.message ?: "Unknown: game already exists")

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleNotReadable(ex: HttpMessageNotReadableException): ResponseEntity<Any> = Problem.builder()
        .withTitle("Invalid JSON")
        .withDetail(ex.message)
        .build()
        .let { return ResponseEntity.status(BAD_REQUEST).body(it) }

    fun handeDefault(ex: Exception): ResponseEntity<Any> =
        ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ex.message ?: "Unknown: An unknown error occurred")
}
