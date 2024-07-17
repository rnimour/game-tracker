package com.rnimour.trials.games

import org.springframework.data.jpa.repository.JpaRepository

/**
 * Spring generates the implementation of this interface
 */
interface GameRepository: JpaRepository<Game, Long> {

    fun findByName(name: String): Game?
}
