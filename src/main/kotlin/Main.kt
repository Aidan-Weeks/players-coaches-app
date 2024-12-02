package ie.setu

import ie.setu.controllers.CoachController
import ie.setu.controllers.PlayerController
import ie.setu.controllers.TeamController
import ie.setu.models.Team
import ie.setu.models.Coach
import ie.setu.models.Player
import ie.setu.utils.readNextInt
import ie.setu.utils.readNextLine
import io.github.oshai.kotlinlogging.KotlinLogging


val logger = KotlinLogging.logger {}
val coachController = CoachController()
val playerController = PlayerController()
val teamController = TeamController()//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    logger.info {"Running Player & Coaches APP"}
    runMenu()
}

fun mainMenu(): Int {
    print(
        """
          > ---------------------------------
          > |     Player & Coaches APP      |
          > ---------------------------------
          > |   1. Coach Options            |
          > --------------------------------- 
          > |   2. Player Options           |
          > ---------------------------------
          > |   8. Save Changes             |
          > |   9. Load Players and Coaches |
          > ---------------------------------
          > |   0. Exit                     |
          > ---------------------------------
          >""".trimMargin(">")
    )
    return readNextInt(" ==> ")
}

fun runMenu() {
    do {
        when (val input = mainMenu()){
            1 -> coachMenu()
            2 -> playerMenu()
            else -> println("Invalid value: $input")
        }
    }while(true)
   }

    fun coachMenu(){
        logger.info { "Launching Coach Menu" }
        val input = readNextInt(
            """
                >------------------------
                >| 1. List Coaches      |
                >| 2. Add Coach         |
                >------------------------
            """.trimMargin(">")
        )
        when(input) {
            1 -> if (coachController.numberOfCoaches() == 0) {
                println("No Coaches on System")
            } else listAllCoaches()
            2 -> createCoach()
        }
    }
fun listAllCoaches(){
    println(coachController.listCoaches())
}

fun createCoach(){
    val coachName = readNextLine("Enter a coaches name: ")
    val coachNumber = readNextInt("Enter the coaches number: ")

    coachController.addCoach(Coach(0, coachName,coachNumber, false))
}

 fun playerMenu(){
    if(coachController.numberOfCoaches() > 0){
        val input = readNextInt(
            """
                >----------------------------
                >| 1. List Players          |
                >| 2. Add Player            |
                >| 3. Add player to Coach   |
                >----------------------------
                
            """.trimMargin(">")
        )
        when(input) {
            1 -> if (playerController.numberOfPlayers() == 0) {
                print("No players on System")
            } else listAllPlayers()

            2 -> createPlayer()
            3 -> if (playerController.numberOfPlayers() == 0){
                println("No players in system")
            } else if(coachController.numberOfCoaches() == 0){
                println("No coaches in system")
            } else addPlayerToTeam()

        }
    }else {
        println("Input invalid - Add coach to the System")
    }
 }

fun listAllPlayers(){
    println(playerController.listAllPlayers())
}

fun createPlayer(){
    val playerName = readNextLine("Enter a players name: ")
    val playerNumber = readNextInt("Enter the players number: ")

    playerController.addPlayer(Player(0, playerName,playerNumber))
}

fun addPlayerToTeam(){
    val playerId = readNextInt("Enter PlayerID: ")
    val coachId = readNextInt("Enter CoachID: ")
    teamController.addPlayerToCoach(playerId, coachId)
}
