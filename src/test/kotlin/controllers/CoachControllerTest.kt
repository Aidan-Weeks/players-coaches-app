package controllers


import ie.setu.controllers.CoachController
import ie.setu.models.Coach
import ie.setu.models.Player
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Nested


class CoachControllerTest {

    private var coachOne: Coach? = null
    private var populatedList: CoachController? = CoachController()
    private var emptyList: CoachController? = CoachController()

    @BeforeEach
    fun setup() {
        coachOne = Coach(123,"John Doe", 831234567, false)

        populatedList!!.addCoach(coachOne!!)
    }

    @AfterEach
    fun tearDown(){
        coachOne = null
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
            assertEquals(1, populatedList!!.numberOfCoaches())
            assertTrue(populatedList!!.numberOfCoaches() > 0)
        }
    }
}