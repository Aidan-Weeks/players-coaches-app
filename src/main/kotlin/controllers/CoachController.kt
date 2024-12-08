package ie.setu.controllers
import ie.setu.models.Coach
import ie.setu.persistence.Serializer
import ie.setu.utils.isValidListIndex
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

    fun findCoachName(searchString: String) =
        formatListString(
            coaches.filter { coach -> coach.name.contains(searchString, ignoreCase = true) }
        )

    fun findCoach(index: Int): Coach? {
        return if (isValidListIndex(index, coaches)) {
            coaches[index]
        }else{
            null
        }
    }

    fun updateCoach(coachToUpdate: Int, coach: Coach?): Boolean {
        val foundCoach = findCoach(coachToUpdate)

        if ((foundCoach != null) && (coach != null)) {
            foundCoach.coachId = coach.coachId
            foundCoach.name = coach.name
            foundCoach.phone = coach.phone
        }
        return true
    }

    fun isValidCoach(index: Int): Boolean {
        return isValidListIndex(index, coaches)
    }

    fun deleteCoach(indexToDelete: Int): Coach?{
        return if (isValidListIndex(indexToDelete, coaches)) {
            coaches.removeAt(indexToDelete)
        }else{
            null
        }
    }

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