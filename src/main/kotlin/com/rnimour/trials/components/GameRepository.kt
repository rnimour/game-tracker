package com.rnimour.trials.components

import com.rnimour.trials.entities.Game
import org.springframework.data.jpa.repository.JpaRepository

interface GameRepository: JpaRepository<Game, Long>
