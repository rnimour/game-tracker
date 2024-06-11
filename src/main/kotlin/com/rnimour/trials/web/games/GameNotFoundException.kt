package com.rnimour.trials.web.games

class GameNotFoundException(id: Long): RuntimeException("Game with id $id not found")
