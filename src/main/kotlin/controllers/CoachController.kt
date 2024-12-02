package ie.setu.controllers
import ie.setu.models.Coach

class CoachController {
    private val coaches = mutableListOf<Coach>()
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
            formatListString(coaches.filter { coaches ->!coaches.isCoachArchived })
        }

    fun numberOfCoaches() = coaches.size


    private fun formatListString(coachesToFormat: List<Coach>): String =
        coachesToFormat
            .joinToString(separator = "\n") { coach ->
                coaches.indexOf(coach).toString() + ": " + coach.toString()
            }
}

