package ie.setu.controllers
import ie.setu.models.Team

class TeamController {
    private val team = mutableListOf<Team>()

    fun addPlayerToCoach(playerId: Int, coachId: Int) {
        team.add(Team(playerId, coachId))
    }
    fun listPlayersInTeam(coachId: Int) = team.filter { it.coachId == coachId } }