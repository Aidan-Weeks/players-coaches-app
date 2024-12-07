package ie.setu.controllers
import ie.setu.models.Player
import ie.setu.models.Team
import ie.setu.persistence.Serializer
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