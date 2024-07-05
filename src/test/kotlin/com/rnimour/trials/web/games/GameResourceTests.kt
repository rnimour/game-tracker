package com.rnimour.trials.web.games

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.rnimour.trials.games.*
import com.rnimour.trials.games.PlayStatus.COMPLETED
import com.rnimour.trials.games.PlayStatus.PLAYING
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.*

// easy way to convert objects to JSON
val prettyGson: Gson = GsonBuilder().setPrettyPrinting().create()
fun GameDTOCreateRequest.toJson(): String = prettyGson.toJson(this)
fun GameDTOUpdateRequest.toJson(): String = prettyGson.toJson(this)
fun Game.toJson(): String = prettyGson.toJson(this)

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

        whenever(gameService.create(gameDTOCreateRequest))
            .thenReturn(game)

        mockMvc.post("/api/games") {
            contentType = APPLICATION_JSON
            content = gameDTOCreateRequest.toJson()
        }.andExpectAll {
            status { isCreated() }
            header { string("Location", "/api/games/${game.id}") }
            content {
                contentType(APPLICATION_JSON)
                json(game.toJson())
            }
        }
    }

    @Test
    fun testCreateGameWhichAlreadyExists() {

        whenever(gameService.create(gameDTOCreateRequest))
            .thenThrow(GameAlreadyExistsException(game.name))

        // Create game with name which already exists
        mockMvc.post("/api/games") {
            contentType = APPLICATION_JSON
            content = gameDTOCreateRequest.toJson()
        }.andExpectAll {
            status { isConflict() }
            content { string(Matchers.containsString("Game with name ${game.name} already exists")) }
        }
    }

    @Test
    fun testReadGame() {

        whenever(gameService.findById(1L))
            .thenReturn(game)

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

        whenever(gameService.findById(1L))
            .thenReturn(game)
        whenever(gameService.updateGame(game, updateGameRequest))
            .thenReturn(updatedGame)

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

        whenever(gameService.findById(1L)).thenReturn(game)

        mockMvc.delete("/api/games/1") {
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
            status { isBadRequest() }
            content {
                string(Matchers.containsString("problem: Parameter specified as non-null is null"))
                string(Matchers.containsString("parameter name"))
            }
        }
    }
}
