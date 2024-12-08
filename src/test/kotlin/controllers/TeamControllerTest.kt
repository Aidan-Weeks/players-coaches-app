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
        val player = Player(111, "Anthony Edwards", 123456789, false)
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
            val newPlayer = Player(112, "Scottie Barnes", 987654321, false)
            val result = populatedList!!.addPlayerToTeam(newPlayer, 1)
            assertTrue(result)

            val teamB = populatedList!!.findTeam(1)
            assertNotNull(teamB)
            assertEquals(2, teamB?.player?.size)
            assertTrue(teamB?.player?.contains(newPlayer) == true)
        }

        @Test
        fun `adding a Player to a non-existent Team should fail`() {
            val newPlayer = Player(113, "Jaylen Brown", 123987654, false)
            val result = populatedList!!.addPlayerToTeam(newPlayer, 999)
            assertFalse(result)
        }
    }

    @Nested
    inner class UpdateTeams {
        @Test
        fun `updating a Team that doesn't exist returns false`() {
            val updatedCoach = Coach(125, "Aaron Gordon", 83678678, false)
            assertFalse(populatedList!!.updateTeam(999, "Updated Team", updatedCoach))
        }

        @Test
        fun `updating an existing Team updates`() {
            val updatedCoach = Coach(124, "steve Kerr", 83678234, false)
            assertTrue(populatedList!!.updateTeam(1, "Updated Team B", updatedCoach))

            val updatedTeam = populatedList!!.findTeam(1)
            assertNotNull(updatedTeam)
            assertEquals("Updated Team B", updatedTeam?.teamName)
            assertEquals(updatedCoach, updatedTeam?.coach)
        }
    }

    @Nested
    inner class RemoveTeams {

        @Test
        fun `removing a Team that doesn't exist returns null`() {
            assertNull(emptyList!!.deleteTeam(0))
            assertNull(populatedList!!.deleteTeam(999))
        }
    }

    @Test
    fun `deleteTeam removes an existing team from list`() {
        assertEquals(3, populatedList!!.numberOfTeams())
        val removedTeam = populatedList!!.deleteTeam(1)
        assertNotNull(removedTeam)
        assertEquals("Team B", removedTeam!!.teamName)
        assertEquals(2, populatedList!!.numberOfTeams())
        assertNull(populatedList!!.findTeam(1))
    }
}