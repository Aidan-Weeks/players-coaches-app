package ie.setu.controllers

import ie.setu.models.Coach

class CoachController {
    private val coaches = mutableListOf<Coach>()
    private var lastId = 0
    private fun getId() = lastId++

    fun addCoach(coach: Coach){
        coach.coachId = getId()
        coaches.add(coach)
    }
    fun listCoaches() = coaches
    fun numberOfCoaches() = coaches.size
}
