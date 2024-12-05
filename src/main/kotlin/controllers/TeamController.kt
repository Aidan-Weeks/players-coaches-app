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


    fun listPlayersInTeam(coachId: Int) = teams.filter { it.coachId == coachId }

    @Throws(Exception::class)
    fun load() {
        teams = serializer.read() as ArrayList<Team>
    }

    @Throws(Exception::class)
    fun store(){
        serializer.write(teams)
    }
}