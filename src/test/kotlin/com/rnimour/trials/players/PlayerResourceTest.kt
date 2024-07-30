package com.rnimour.trials.players

import com.rnimour.trials.games.Game
import com.rnimour.trials.games.GameRepository
import com.rnimour.trials.games.GameService
import com.rnimour.trials.games.PlayStatus
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.*

// easy way to convert objects to JSON without using Gson
val prettyJson: Json = Json { prettyPrint = true }
fun PlayerDTOCreateRequest.toJson(): String = prettyJson.encodeToString(this)
fun PlayerDTOUpdateRequest.toJson(): String = prettyJson.encodeToString(this)
fun Player.toJson(): String = prettyJson.encodeToString(this)

@WebMvcTest
class PlayerResourceTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var gameService: GameService
    @MockBean
    private lateinit var gameRepository: GameRepository
    @MockBean
    private lateinit var playerService: PlayerService
    @MockBean
    private lateinit var playerRepository: PlayerRepository

    val playerDTOCreateRequest = PlayerDTOCreateRequest(name = "Link") // Hyah!
    val player = Player(
        id = 1L,
        name = "Link",
        favoriteGame = null,
        gamesPlayed = mutableListOf()
    )

    @Test
    fun testCreatePlayer() {

        whenever(playerService.create(playerDTOCreateRequest)).thenReturn(player)

        mockMvc.post("/api/players") {
            contentType = APPLICATION_JSON
            content = playerDTOCreateRequest.toJson()
        }.andExpectAll {
            status { isCreated() }
            header { string("Location", "/api/players/${player.id}") }
            content {
                contentType(APPLICATION_JSON)
                json(player.toJson())
            }
        }
    }

    @Test
    fun testCreatePlayerWhichAlreadyExists() {

        whenever(playerService.create(playerDTOCreateRequest)).thenThrow(PlayerAlreadyExistsException(player.name))

        // Create player with name which already exists
        mockMvc.post("/api/players") {
            contentType = APPLICATION_JSON
            content = playerDTOCreateRequest.toJson()
        }.andExpectAll {
            status { isConflict() }
            content { string(Matchers.containsString("Player with name ${player.name} already exists")) }
        }
    }

    @Test
    fun testReadPlayer() {

        whenever(playerService.findByIdOrThrow(1L)).thenReturn(player)

        mockMvc.get("/api/players/1") {
            contentType = APPLICATION_JSON
        }.andExpectAll {
            status { isOk() }
            content { json(player.toJson()) }
        }
    }

    @Test
    fun testUpdatePlayer() {
        //given
        val oldFavoriteGame = mockGameWithName("Breath of the Wild")
        val newFavoriteGame = mockGameWithName("Tears of the Kingdom")
        player.favoriteGame = oldFavoriteGame
        val updatePlayerRequest = PlayerDTOUpdateRequest(favoriteGame = newFavoriteGame.name)
        val updatedPlayer = player.copy(newFavoriteGame = newFavoriteGame)

        // when
        whenever(playerService.findByIdOrThrow(1L)).thenReturn(player)
        whenever(playerService.updatePlayer(player, updatePlayerRequest)).thenReturn(updatedPlayer)

        // then
        mockMvc.put("/api/players/1") {
            contentType = APPLICATION_JSON
            content = updatePlayerRequest.toJson()
        }.andExpectAll {
            status { isOk() }
            content { json(updatedPlayer.toJson()) }
        }
    }

    private fun mockGameWithName(name: String) =
        mock(Game::class.java)
            .also {
                whenever(it.name).thenReturn(name)
                whenever(it.playStatus).thenReturn(PlayStatus.PLAYING)
                whenever(it.developer).thenReturn("Nintendo")
            }

    @Test
    fun testDeletePlayer() {

        whenever(playerService.findByIdOrThrow(1L)).thenReturn(player)

        mockMvc.delete("/api/players/1") {
            contentType = APPLICATION_JSON
        }.andExpectAll {
            status { isOk() }
            content { json(player.toJson()) }
        }
    }

    @Test
    fun testCreatePlayerWithInvalidRequest() {

        // missing required field 'name'
        mockMvc.post("/api/players") {
            contentType = APPLICATION_JSON
            content = """
                {
                    "favoriteGame": "Breath of the Wild"
                }
            """.trimIndent()
        }.andExpectAll {
            status { isBadRequest() }
            content {
                string(Matchers.containsString("Invalid JSON"))
                string(Matchers.matchesRegex(".*Field 'name' is required for type .*PlayerDTOCreateRequest.*"))
            }
        }
    }

    private fun Player.copy(newFavoriteGame: Game): Player = Player(
        id = id,
        name = name,
        favoriteGame = newFavoriteGame,
        gamesPlayed = gamesPlayed
    )
}