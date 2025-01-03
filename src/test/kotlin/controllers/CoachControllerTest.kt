package controllers


import ie.setu.controllers.CoachController
import ie.setu.models.Coach
import ie.setu.updateCoach
import ie.setu.utils.isValidListIndex
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import persistence.XMLSerializer
import java.io.File


class CoachControllerTest {

    private var coachOne: Coach? = null
    private var coachTwo: Coach? = null
    private var coachThree: Coach? = null
    private var populatedList: CoachController? = CoachController(XMLSerializer(File("coaches.xml")))
    private var emptyList: CoachController? = CoachController(XMLSerializer(File("empty-coaches.xml")))

    @BeforeEach
    fun setup() {
        coachOne = Coach(123,"John Doe", 831234567, false)
        coachTwo = Coach(124, "Barry King", 832345678, false)
        coachThree = Coach(125, "Joe McGrath", 833456789, false)

        populatedList!!.addCoach(coachOne!!)
        populatedList!!.addCoach(coachTwo!!)
        populatedList!!.addCoach(coachThree!!)
    }

    @AfterEach
    fun tearDown(){
        coachOne = null
        coachTwo = null
        coachThree = null
    }

    @Nested
    inner class AddCoaches {
        @Test
        fun `adding a Coach to populated list adds to ArrayList`() {
            val newCoach = Coach(124, "Jane Doe", 842345678, false)
            assertTrue(populatedList!!.addCoach(newCoach))
        }
    }
    @Nested
    inner class ListCoaches {
        @Test
        fun `ListCoaches returns coaches in ArrayList`() {
            assertEquals(0, emptyList!!.numberOfCoaches())
            assertTrue(emptyList!!.listCoaches().lowercase().contains("no coaches"))
            assertEquals(3, populatedList!!.numberOfCoaches())
            assertTrue(populatedList!!.numberOfCoaches() > 0)
        }
    }

    @Nested
    inner class PersistenceTest{
        @Test
    fun `saving and loading an empty coaches collection in XML doesn't crash app`() {
        val storingCoaches = CoachController(XMLSerializer(File("coach.xml")))
        storingCoaches.store()
        val loadedCoaches = CoachController(XMLSerializer(File("coach.xml")))
        loadedCoaches.load()

        assertEquals(0, storingCoaches.numberOfCoaches())
        assertEquals(0, loadedCoaches.numberOfCoaches())
        assertEquals(storingCoaches.numberOfCoaches(), loadedCoaches.numberOfCoaches())
    }

        @Test
        fun `saving and loading a loaded collection in XML doesn't loose data`() {
            // Storing 3 notes to the notes.XML file.
            val storingCoaches = CoachController(XMLSerializer(File("coach.xml")))
            storingCoaches.addCoach(coachOne!!)
            storingCoaches.addCoach(coachTwo!!)
            storingCoaches.addCoach(coachThree!!)
            storingCoaches.store()

            val loadedPlayers = CoachController(XMLSerializer(File("coach.xml")))
            loadedPlayers.load()

            assertEquals(3, storingCoaches.numberOfCoaches())
            assertEquals(3, loadedPlayers.numberOfCoaches())
            assertEquals(storingCoaches.numberOfCoaches(), loadedPlayers.numberOfCoaches())
        }
    }

    @Nested
    inner class updateCoaches {
        @Test
        fun `updating a Coach that doesn't exist returns false`() {
            assertTrue(populatedList!!.updateCoach(123,null))
        }
        @Test
        fun `update Coach that exists returns true and updates`(){
            val originalCoach = populatedList!!.findCoach(1)
            assertNotNull(originalCoach)
            if (originalCoach != null) {
                assertEquals("Barry King", originalCoach.name)
            }

            val updatedCoach = Coach(124, "Jim", 890123456, false)
            val updatedResult = populatedList!!.updateCoach(1, updatedCoach)
            assertEquals(true, updatedResult)
        }
    }

    @Nested
    inner class deleteCoach {
        @Test
        fun `deleting a Coach that doesn't exist, returns null`() {
            assertNull(emptyList!!.deleteCoach(0))
            assertNull(populatedList!!.deleteCoach(8))
        }

    @Test
    fun `remove an existing coach and return that coach was deleted`(){
        assertEquals(3, populatedList!!.numberOfCoaches())
        assertEquals(coachOne, populatedList!!.deleteCoach(0))
        assertEquals(2, populatedList!!.numberOfCoaches())
    }
    }
}

