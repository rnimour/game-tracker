package com.rnimour.trials.web.games

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.rnimour.trials.games.*
import com.rnimour.trials.games.PlayStatus.COMPLETED
import com.rnimour.trials.games.PlayStatus.PLAYING
import org.junit.jupiter.api.Test
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.*
import java.util.*

@WebMvcTest
class GameResourceTests {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var gameRepository: GameRepository

    @MockBean
    private lateinit var gameService: GameService

    val gameDTOCreateRequest = GameDTOCreateRequest(
        name = "Trackmania",
        playStatus = PLAYING,
        releaseYear = 2020,
        developer = "Nadeo",
        series = "Trackmania",
        genre = "Racing",
    )

    val game = Game(
        id = 123L,
        name = "Trackmania",
        playStatus = PLAYING,
        releaseYear = 2020,
        developer = "Nadeo",
        series = "Trackmania",
        genre = "Racing",
    )

    @Test
    fun testCreateGame() {

        whenever(gameService.create(gameDTOCreateRequest)).thenReturn(game)

        mockMvc.post("/api/games") {
            contentType = APPLICATION_JSON
            content = gameDTOCreateRequest.toJson()
        }.andExpectAll {
            status { isCreated() }
            header { string("Location", "/api/games/${game.id}") }
            content { contentType(APPLICATION_JSON) }
            content { json(game.toJson()) }
        }
    }

    @Test
    fun testReadGame() {

        whenever(gameRepository.findById(1L)).thenReturn(Optional.of(game))

        mockMvc.get("/api/games/1") {
            contentType = APPLICATION_JSON
        }.andExpectAll {
            status { isOk() }
            content { json(game.toJson()) }
        }
    }

    @Test
    fun testUpdateGame() {

        val updatedPlayStatus = COMPLETED
        val updatedGenre = "Racing, Platformer"

        gameDTOCreateRequest.playStatus = updatedPlayStatus
        gameDTOCreateRequest.genre = updatedGenre
        val updateGameRequest = GameDTOUpdateRequest(playStatus = updatedPlayStatus, genre = updatedGenre)
        val updatedGame = game.copy(playStatus = updatedPlayStatus, genre = updatedGenre)

        whenever(gameRepository.findById(1L)).thenReturn(Optional.of(game))
        whenever(gameService.updateGame(game, updateGameRequest)).thenReturn(updatedGame)

        mockMvc.patch("/api/games/1") {
            contentType = APPLICATION_JSON
            content = updateGameRequest.toJson()
        }.andExpectAll {
            status { isOk() }
            content { json(updatedGame.toJson()) }
        }
    }

    @Test
    fun testDeleteGame() {

        whenever(gameRepository.findById(1L)).thenReturn(Optional.of(game))

        mockMvc.delete("/api/games/1") {
            contentType = APPLICATION_JSON
        }.andExpectAll {
            status { isOk() }
            content { json(game.toJson()) }
        }
    }

    @Test
    fun testCreateGameWithInvalidRequest() {

        // missing required field 'name'
        mockMvc.post("/api/games") {
            contentType = APPLICATION_JSON
            content = """
                {
                    "playStatus": "PLAYING",
                    "releaseYear": 2020,
                    "developer": "Nadeo",
                    "series": "Trackmania",
                    "genre": "Racing"
                }
            """.trimIndent()
        }.andExpectAll {
            status { isBadRequest() } // TODO: make a neat error message which tells user which field is missing. Use Zalando Problem
        }
    }
}

// easy way to convert objects to JSON
val prettyGson: Gson = GsonBuilder().setPrettyPrinting().create()
fun GameDTOCreateRequest.toJson(): String = prettyGson.toJson(this)
fun GameDTOUpdateRequest.toJson(): String = prettyGson.toJson(this)
fun Game.toJson(): String = prettyGson.toJson(this)
