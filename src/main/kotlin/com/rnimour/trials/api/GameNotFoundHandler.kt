package com.rnimour.trials.api

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GameNotFoundHandler {

    @ExceptionHandler(GameNotFoundException::class)
    fun handleGameNotFound(ex: GameNotFoundException): ResponseEntity<Any> {
        return ResponseEntity.status(404).body(ex.message!!)
    }
}
