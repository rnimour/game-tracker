package com.rnimour.trials.api

class GameNotFoundException(id: Long): RuntimeException("Game with id $id not found")
