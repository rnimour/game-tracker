package com.rnimour.trials.games

import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.empty
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
class GameRepositoryTest {

    @Autowired
    private lateinit var gameRepository: GameRepository

    val game = Game(
        name = "Octopath Traveler",
        playStatus = PlayStatus.PLAYING,
        releaseYear = 2018,
        developer = "Square Enix",
        genre = "Role-playing",
    )

    @Test
    fun testSaveAndCreateId() {
        val savedGame = gameRepository.save(game)
        assertThat("id should be created", savedGame.id, notNullValue())
    }

    @Test
    fun testFindById() {
        val foundGame = gameRepository.findById(1)
        assertThat("should find Zelda (omg literally Link)", foundGame.isPresent)
    }

    @Test fun testExistsById() = assertThat("should exist", gameRepository.existsById(1))
    @Test fun testFindAll() = assertThat("should find some games", gameRepository.findAll(), not(empty()))
    @Test fun testCount() = assertThat("should find the 4 default games", gameRepository.count(), equalTo(4))
    @Test fun testCountExtra() {
        gameRepository.save(game)
        assertThat("should find the extra game", gameRepository.count(), equalTo(5))
    }

    @Test
    fun testDeleteById() {
        gameRepository.deleteById(1)
        val foundGame = gameRepository.findById(1)
        assertThat("should not find Zelda (Link is crying)", foundGame.isEmpty)
    }

    @Test
    fun testDelete() {
        val foundGame = gameRepository.findById(1)
        assertThat("should find Zelda (Link is happy)", foundGame.isPresent)
        gameRepository.delete(foundGame.get())
        val foundGameAfterDelete = gameRepository.findById(1)
        assertThat("should not find Zelda (Link is crying)", foundGameAfterDelete.isEmpty)
    }

    @Test
    fun testDeleteAll() {
        gameRepository.deleteAll()
        assertThat("should not find any games", gameRepository.findAll(), empty())
    }
}