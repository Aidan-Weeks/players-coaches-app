
package controllers
import ie.setu.controllers.PlayerController
import ie.setu.models.Coach
import ie.setu.models.Player
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import persistence.XMLSerializer
import java.io.File



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

    private var playerTwo: Player? = null
    private var playerThree: Player? = null
    private var populatedList: PlayerController? = PlayerController(XMLSerializer(File("players.xml")))
    private var emptyList: PlayerController? = PlayerController(XMLSerializer(File("empty-Players.xml")))


    @BeforeEach
    fun setup() {
        playerOne = Player(123,"John Doe", 831234567, false)
        playerTwo = Player(124,"Kevin Walsh", 832345678, false)
        playerThree = Player(125,"Martin Mcgrath", 83345678, false)

        populatedList!!.addPlayer(playerOne!!)
        populatedList!!.addPlayer(playerTwo!!)
        populatedList!!.addPlayer(playerThree!!)
    }


    @AfterEach
    fun tearDown(){
        playerOne = null
        playerTwo = null
        playerThree = null
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
            assertFalse(emptyList!!.listPlayers().lowercase().contains("No players"))
            assertEquals(3, populatedList!!.numberOfPlayers())
            assertTrue(populatedList!!.numberOfPlayers() > 0)
        }
    }

    @Nested
    inner class PersistenceTest{
        @Test
        fun `saving and loading an empty Players collection in XML doesn't crash app`() {
            val storingPlayers = PlayerController(XMLSerializer(File("coach.xml")))
            storingPlayers.store()
            val loadedCoaches = PlayerController(XMLSerializer(File("coach.xml")))
            loadedCoaches.load()

            assertEquals(0, storingPlayers.numberOfPlayers())
            assertEquals(0, loadedCoaches.numberOfPlayers())
            assertEquals(storingPlayers.numberOfPlayers(), loadedCoaches.numberOfPlayers())
        }

        @Test
        fun `saving and loading a loaded collection in XML doesn't loose data`() {
            // Storing 3 notes to the notes.XML file.
            val storingPlayers = PlayerController(XMLSerializer(File("coach.xml")))
            storingPlayers.addPlayer(playerOne!!)
            storingPlayers.addPlayer(playerTwo!!)
            storingPlayers.addPlayer(playerThree!!)
            storingPlayers.store()

            val loadedPlayers = PlayerController(XMLSerializer(File("coach.xml")))
            loadedPlayers.load()

            assertEquals(3, storingPlayers.numberOfPlayers())
            assertEquals(3, loadedPlayers.numberOfPlayers())
            assertEquals(storingPlayers.numberOfPlayers(), loadedPlayers.numberOfPlayers())
        }
    }

    @Nested
    inner class updatePlayers{
        @Test
        fun `updating a Player that doesn't exist returns false`() {
            assertTrue(populatedList!!.updatePlayer(123,null))
        }
        @Test
        fun `update Player that exists returns true and updates`(){
            val originalCoach = populatedList!!.findPlayer(0)
            assertNotNull(originalCoach)
            if (originalCoach != null) {
                assertEquals("John Doe", originalCoach.name)
            }

            val updatedPlayer = Player(124, "Jim", 890123456, false)
            val updatedResult = populatedList!!.updatePlayer(1, updatedPlayer)
            assertEquals(true, updatedResult)
        }
    }

    @Nested
    inner class deletePlayer {
        @Test
        fun `deleting a Player that doesn't exist, returns null`() {
            assertNull(emptyList!!.deletePlayer(0))
            assertNull(populatedList!!.deletePlayer(8))
        }

        @Test
        fun `remove an existing Player and return that Player was deleted`(){
            assertEquals(3, populatedList!!.numberOfPlayers())
            assertEquals(playerOne, populatedList!!.deletePlayer(0))
            assertEquals(2, populatedList!!.numberOfPlayers())
        }
    }
}


