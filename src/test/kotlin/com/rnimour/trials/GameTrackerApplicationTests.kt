package com.rnimour.trials

import com.rnimour.trials.games.GameRepository
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.empty
import org.hamcrest.Matchers.not
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class GameTrackerApplicationTests {

    @Autowired
    private lateinit var gameRepository: GameRepository

    @Test
    fun contextLoads() {
        // Just print all games to see if the database initialization is working
        gameRepository.findAll().also {
            assertThat("should find some games", it, not(empty()))
        }.forEach(::println)
    }

}
