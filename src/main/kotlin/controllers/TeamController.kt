package ie.setu.controllers
import ie.setu.models.Team

class TeamController {
    private val team = mutableListOf<Team>()
    fun addPlayerToTeam(playerId: Int, TeamId: Int) {
        team.add(Team(playerId, TeamId))
    }
    fun listPlayersInTeam(TeamId: Int) = team.filter { it.TeamId == TeamId } }