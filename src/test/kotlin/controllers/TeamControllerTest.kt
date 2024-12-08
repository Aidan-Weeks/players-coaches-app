package controllers

import ie.setu.controllers.TeamController
import ie.setu.models.Coach
import ie.setu.models.Team
import ie.setu.models.Player
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import persistence.XMLSerializer
import java.io.File

class TeamControllerTest {

    private var teamOne: Team? = null
    private var teamTwo: Team? = null
    private var teamThree: Team? = null
    private var coach: Coach? = null
    private var populatedList: TeamController? = TeamController(XMLSerializer(File("teams.xml")))
    private var emptyList: TeamController? = TeamController(XMLSerializer(File("empty-teams.xml")))

    @BeforeEach
    fun setup() {
        coach = Coach(123, "John Doe", 831234567, false)
        val player = Player(111, "Player A", 123456789, false)
        teamOne = Team(0, "Team A", mutableListOf(player), coach!!, false)
        teamTwo = Team(1, "Team B", mutableListOf(player), coach!!, false)
        teamThree = Team(2, "Team C", mutableListOf(player), coach!!, false)

        populatedList!!.addTeam(teamOne!!)
        populatedList!!.addTeam(teamTwo!!)
        populatedList!!.addTeam(teamThree!!)
    }

    @AfterEach
    fun tearDown() {
        teamOne = null
        teamTwo = null
        teamThree = null
    }

    @Nested
    inner class AddTeams {
        @Test
        fun `adding a Team to populated list adds to ArrayList`() {
            val newTeam = Team(3, "Team D", mutableListOf(), coach!!, false)
            assertTrue(populatedList!!.addTeam(newTeam))
        }
    }

    @Nested
    inner class ListTeams {
        @Test
        fun `ListTeams returns teams in ArrayList`() {
            assertEquals(0, emptyList!!.numberOfTeams())
            assertTrue(emptyList!!.listTeams().lowercase().contains("no teams"))
            assertEquals(3, populatedList!!.numberOfTeams())
            assertTrue(populatedList!!.numberOfTeams() > 0)
        }
    }

    @Nested
    inner class AddPlayerToTeam {
        @Test
        fun `adding a Player to an existing Team should succeed`() {
            val newPlayer = Player(112, "Player B", 987654321, false)
            val result = populatedList!!.addPlayerToTeam(newPlayer, 1)  // Add to Team B
            assertTrue(result)

            val teamB = populatedList!!.findTeam(1)
            assertNotNull(teamB)
            assertEquals(2, teamB?.player?.size)
            assertTrue(teamB?.player?.contains(newPlayer) == true)
        }

        @Test
        fun `adding a Player to a non-existent Team should fail`() {
            val newPlayer = Player(113, "Player C", 123987654, false)
            val result = populatedList!!.addPlayerToTeam(newPlayer, 999)  // Invalid Team ID
            assertFalse(result)
        }
    }
}