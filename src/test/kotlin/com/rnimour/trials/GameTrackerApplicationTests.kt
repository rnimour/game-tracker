package com.rnimour.trials

import com.rnimour.trials.players.PlayerService
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class GameTrackerApplicationTests {

    @Autowired
    private lateinit var playerService: PlayerService

    @Test
    fun contextLoads() {

        playerService.findAll()
            .apply { assertThat("should find some players", isNotEmpty()) }
            .forEach { println(it) }
        assertThat("If this test fails, something went seriously wrong", true)
    }
}
