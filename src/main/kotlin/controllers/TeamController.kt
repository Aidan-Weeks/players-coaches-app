package ie.setu.controllers
import ie.setu.models.Coach
import ie.setu.models.Player
import ie.setu.models.Team
import ie.setu.persistence.Serializer
import ie.setu.utils.isValidListIndex
import kotlin.jvm.Throws

class TeamController(serializerType: Serializer) {
    private var serializer: Serializer = serializerType
    private var teams = mutableListOf<Team>()
    private var lastId = 0
    private fun getId() = lastId++

    fun addTeam(team: Team) :Boolean {
        team.teamId = getId()
        return teams.add(team)
    }

    fun numberOfTeams() = teams.size

   fun listTeams(): String =
       if (teams.isEmpty()) {
           "No Teams in system"
       }else{
           formatListString(teams.filter { teams ->!teams.isTeamArchived })
       }

    fun findTeam(index: Int): Team? {
        return if (isValidListIndex(index, teams)){
            teams[index]
        }else{
            null
        }
    }

    fun addPlayerToTeam(player: Player, teamId: Int): Boolean {
        val team = findTeam(teamId)
        return if (team != null) {
            team.player.add(player)
            true
        } else {
            false
        }
    }

    fun removePlayerFromTeam(playerId: Int, teamId: Int): Boolean {
        val team = findTeam(teamId)
        if(team != null) {
            val player = team.player.find {it.playerId == playerId}
            if(player != null) {
                team.player.remove(player)
                return true
            }
        }
        return false
    }


    fun updateTeam(teamToUpdate: Int, teamName: String, coach: Coach): Boolean {
        val foundTeam = findTeam(teamToUpdate)

        return if (foundTeam != null) {
            foundTeam.teamName = teamName
            foundTeam.coach = coach
            true
        } else {
            false
        }
    }

    fun deleteTeam(indexToDelete: Int): Team? {
        return if (isValidListIndex(indexToDelete, teams)){
            teams.removeAt(indexToDelete)
        }else{
            null
        }
    }

    fun isValidTeam(index: Int): Boolean {
        return isValidListIndex(index, teams)
    }

    private fun formatListString(playersToFormat: List<Team>): String =
        playersToFormat
            .joinToString(separator = "\n") { team ->
                teams.indexOf(team).toString() + ": " + team.toString()
            }

    @Throws(Exception::class)
    fun load() {
        teams = serializer.read() as ArrayList<Team>
    }

    @Throws(Exception::class)
    fun store(){
        serializer.write(teams)
    }
}