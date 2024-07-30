package com.rnimour.trials.games

import com.google.gson.*
import com.rnimour.trials.games.PlayStatus.COMPLETED
import com.rnimour.trials.games.PlayStatus.PLAYING
import com.rnimour.trials.players.PlayerService
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.*
import java.lang.reflect.Type


// easy way to convert objects to JSON
val prettyGson: Gson =
    GsonBuilder().setPrettyPrinting()
        // actually never mind. This is no longer easy. This is added to serialize empty collections as null
        .registerTypeHierarchyAdapter(Collection::class.java, IgnoreEmptyCollectionJsonSerializer()).create()

fun GameDTOCreateRequest.toJson(): String = prettyGson.toJson(this)
fun GameDTOUpdateRequest.toJson(): String = prettyGson.toJson(this)
fun Game.toJson(): String = prettyGson.toJson(this)

@WebMvcTest
class GameResourceTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var gameService: GameService

    @MockBean
    private lateinit var gameRepository: GameRepository

    @MockBean
    private lateinit var playerService: PlayerService

    private val gameDTOCreateRequest = GameDTOCreateRequest(
        name = "Trackmania",
        playStatus = PLAYING,
        releaseYear = 2020,
        developer = "Nadeo",
        series = "Trackmania",
        genre = "Racing",
    )

    private val game = Game(
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
            content {
                contentType(APPLICATION_JSON)
                json(game.toJson())
            }
        }
    }

    @Test
    fun testCreateGameWhichAlreadyExists() {

        whenever(gameService.create(gameDTOCreateRequest)).thenThrow(GameAlreadyExistsException(game.name))

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

        whenever(gameService.findByIdOrThrow(1L)).thenReturn(game)

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
        val updatedGame = game.copy(newPlayStatus = updatedPlayStatus, newGenre = updatedGenre)
        assert(game.playStatus == PLAYING) // do not update the original game

        whenever(gameService.findByIdOrThrow(1L)).thenReturn(game)
        whenever(gameService.updateGame(game, updateGameRequest)).thenReturn(updatedGame)

        mockMvc.put("/api/games/1") {
            contentType = APPLICATION_JSON
            content = updateGameRequest.toJson()
        }.andExpectAll {
            status { isOk() }
            content { json(updatedGame.toJson()) }
        }
    }

    @Test
    fun testDeleteGame() {

        whenever(gameService.findByIdOrThrow(1L)).thenReturn(game)

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
            status { isBadRequest() }
            content {
                string(Matchers.containsString("Invalid JSON"))
                string(Matchers.matchesRegex(".*Field 'name' is required for type .*GameDTOCreateRequest.*"))
            }
        }
    }
}

private fun Game.copy(newPlayStatus: PlayStatus, newGenre: String): Game = Game(
    id = id,
    name = name,
    playStatus = newPlayStatus,
    releaseYear = releaseYear,
    developer = developer,
    series = series,
    genre = newGenre,
)

// the implementation of the completely cursed way to serialize empty collections as null.
// Since that is how the API returns the data. Yes, behaviour-driven testing.
// No, not test-driven development, not behaviour-driven development, but behaviour-driven testing.
private class IgnoreEmptyCollectionJsonSerializer : JsonSerializer<List<*>?> {
    override fun serialize(src: List<*>?, typeOfSrc: Type?, context: JsonSerializationContext): JsonElement? =
        if (src == null || src.isEmpty()) null
        else {
            // all these scope functions, just to make this an expression body...
            // All this does is initialize a JsonArray and add all serialized elements to it
            src.map { context.serialize(it) }
                .fold(JsonArray()) { array, jsonElement ->
                    array.apply { add(jsonElement) }
                }
        }
}
