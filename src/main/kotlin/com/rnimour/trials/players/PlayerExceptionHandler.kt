package com.rnimour.trials.players

import org.springframework.http.HttpStatus.*
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.zalando.problem.Problem
import org.zalando.problem.Status

class PlayerAlreadyExistsException(name: String) : RuntimeException("Player with name $name already exists")
class PlayerNotFoundException(id: Long): RuntimeException("Player with id $id not found")

@ControllerAdvice
class PlayerExceptionHandler {

    @ExceptionHandler(PlayerNotFoundException::class)
    fun handlePlayerNotFound(ex: PlayerNotFoundException): ResponseEntity<Any> = Problem.builder()
        .withTitle(ex.message ?: "Player not found")
        .withStatus(Status.NOT_FOUND)
        .build().let { ResponseEntity.status(NOT_FOUND).body(it) }


    @ExceptionHandler(PlayerAlreadyExistsException::class)
    fun handlePlayerAlreadyExists(ex: PlayerAlreadyExistsException): ResponseEntity<Any> = Problem.builder()
        .withTitle(ex.message ?: "Player already exists")
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