package controllers


import ie.setu.controllers.CoachController
import ie.setu.models.Coach
import ie.setu.models.Player
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Nested


class CoachControllerTest {

    private var coachOne: Coach? = null
    private var populatedList: CoachController? = CoachController()
    private var emptyList: CoachController? = CoachController()

    @BeforeEach
    fun setup() {
        coachOne = Coach(123,"John Doe", 831234567)

        populatedList!!.addCoach(coachOne!!)
    }

    @AfterEach
    fun tearDown(){
        coachOne = null
    }

    @Nested
    inner class addPlayers {
        @Test
        fun `adding a Coach to populated list adds to ArrayList`() {
            val newCoach = Coach(124, "Jane Doe", 842345678)
            assertTrue(populatedList!!.addCoach(newCoach))
        }
    }
}