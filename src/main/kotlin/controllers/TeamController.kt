package ie.setu.controllers
import ie.setu.models.Player
import ie.setu.models.Team
import ie.setu.persistence.Serializer
import kotlin.jvm.Throws

class TeamController(serializerType: Serializer) {
    private var serializer: Serializer = serializerType
    private var team = mutableListOf<Team>()

    fun addPlayerToTeam(playerId: Int, coachId: Int) :Boolean {
        return team.add(Team(playerId, coachId))
    }
    fun listPlayersInTeam(coachId: Int) = team.filter { it.coachId == coachId }

    @Throws(Exception::class)
    fun load() {
        team = serializer.read() as ArrayList<Team>
    }

    @Throws(Exception::class)
    fun store(){
        serializer.write(team)
    }
}