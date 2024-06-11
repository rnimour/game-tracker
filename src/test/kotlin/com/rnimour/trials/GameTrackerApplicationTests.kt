package com.rnimour.trials

import com.rnimour.trials.games.GameRepository
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class GameTrackerApplicationTests {

    @Autowired
    private lateinit var gameRepository: GameRepository

    @Test
    fun contextLoads() {
        gameRepository.findAll().forEach(::println)
    }

}
