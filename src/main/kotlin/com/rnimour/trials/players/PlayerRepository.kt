package com.rnimour.trials.players

import org.springframework.data.jpa.repository.JpaRepository

/**
 * Spring generates the implementation of this interface
 */
interface PlayerRepository: JpaRepository<Player, Long> {

    fun findByName(name: String): Player?
}
