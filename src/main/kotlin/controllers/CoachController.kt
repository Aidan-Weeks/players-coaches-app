package ie.setu.controllers
import ie.setu.models.Coach
import ie.setu.persistence.Serializer
import kotlin.jvm.Throws

class CoachController(serializerType: Serializer) {
    private var serializer: Serializer = serializerType
    private var coaches = mutableListOf<Coach>()
    private var lastId = 0
    private fun getId() = lastId++

    fun addCoach(coach: Coach) : Boolean{
        coach.coachId = getId()
        return coaches.add(coach)
    }
    fun listCoaches(): String =
        if (coaches.isEmpty()) {
            "No coaches in system"
        }else{
            formatListString(coaches.filter { coach ->!coach.isCoachArchived })
        }

    fun numberOfCoaches() = coaches.size

    fun findCoachName(searchName: String) =
        formatListString(
            coaches.filter { coach -> coach.name.contains(searchName, ignoreCase = true) }
        )

    fun listPlayersInTeam(coachId: Int) = coaches.filter { it.coachId == coachId }


    private fun formatListString(coachesToFormat: List<Coach>): String =
        coachesToFormat
            .joinToString(separator = "\n") { coach ->
                coaches.indexOf(coach).toString() + ": " + coach.toString()
            }

    @Throws(Exception::class)
    fun load() {
        coaches = serializer.read() as ArrayList<Coach>
    }

    @Throws(Exception::class)
    fun store(){
        serializer.write(coaches)
    }
}