package ie.setu

import ie.setu.controllers.CoachController
import ie.setu.controllers.PlayerController
import ie.setu.controllers.TeamController
import ie.setu.utils.readNextInt

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
val coachController = CoachController()
val playerController = PlayerController()
val teamController = TeamController()
    mainMenu()
}

fun mainMenu(): Int {
    print(
        """
          > ---------------------------------
          > |     Player & Coaches APP      |
          > ---------------------------------
          > |   1. PLayer Options           |
          > --------------------------------- 
          > |   2. Coach Options            |
          > ---------------------------------
          > |   8. Save Changes             |
          > |   9. Load Players and Coaches |
          > ---------------------------------
          > |   0. Exit                     |
          > ---------------------------------
        """.trimMargin(">")
    )
    return readNextInt(" ==> ")
}
