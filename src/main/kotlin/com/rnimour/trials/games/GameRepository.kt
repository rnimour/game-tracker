package com.rnimour.trials.games

import org.springframework.data.jpa.repository.JpaRepository

interface GameRepository: JpaRepository<Game, Long>
