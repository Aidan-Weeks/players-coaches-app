package controllers


import ie.setu.controllers.PlayerController
import ie.setu.models.Player
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Nested


class PlayerControllerTest {

    private var playerOne: Player? = null
    private var populatedList: PlayerController? = PlayerController()
    private var emptyList: PlayerController? = PlayerController()

    @BeforeEach
    fun setup() {
        playerOne = Player(123,"John Doe", 831234567, false)

        populatedList!!.addPlayer(playerOne!!)
    }

    @AfterEach
    fun tearDown(){
        playerOne = null
    }

    @Nested
    inner class AddPlayers{
        @Test
        fun `adding a Player to populated list adds to ArrayList`() {
            val newPlayer = Player(124, "Jane Doe", 842345678, false)
            assertTrue(populatedList!!.addPlayer(newPlayer))
        }
    }
    @Nested
    inner class ListPlayers {
        @Test
        fun `ListPlayers returns players in ArrayList`() {
            assertEquals(0, emptyList!!.numberOfPlayers())
            assertTrue(emptyList!!.listPlayers().lowercase().contains("No players"))
            assertEquals(1, populatedList!!.numberOfPlayers())
            assertTrue(populatedList!!.numberOfPlayers() > 0)
        }
    }
}