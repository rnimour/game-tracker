package com.rnimour.trials.web.games

import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.zalando.problem.Problem

@ControllerAdvice
class GameExceptionHandler {

    @ExceptionHandler(GameNotFoundException::class)
    fun handleGameNotFound(ex: GameNotFoundException): ResponseEntity<Any> {
        return ResponseEntity.status(404).body(ex.message!!)
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleNotReadeable(ex: HttpMessageNotReadableException): ResponseEntity<Any> = Problem.builder()
        .withTitle("Invalid JSON")
        .withDetail(ex.message)
        .build()
        .let { return ResponseEntity.status(400).body(it) }

    fun handeDefault(ex: Exception): ResponseEntity<Any> {
        return ResponseEntity.status(500).body(ex.message!!)
    }
}
