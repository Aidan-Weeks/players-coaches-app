package ie.setu

import ie.setu.controllers.CoachController
import ie.setu.controllers.PlayerController
import ie.setu.controllers.TeamController
import ie.setu.models.Player
import ie.setu.utils.readNextInt
import ie.setu.utils.readNextLine

val coachController = CoachController()
val playerController = PlayerController()
val teamController = TeamController()//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
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
          >""".trimMargin(">")
    )
    return readNextInt(" ==> ")
}

fun runMenu() {
    do {
        when (val input = mainMenu()){
            1 -> playerMenu()
            2 -> coachMenu()
            8 -> save()
            9 -> load()
            0 -> exit()
            else -> println("Invalid value: $input")
        }
    }while(true)
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
            } else if(CoachController.numberOfCoaches() == 0){
                println("No coaches in system")
            } else addPlayerToGroup()

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

fun addPlayerToGroup(){

}

fun coachMenu(){
    val input = readNextInt(
        """
            >------------------------
            >| 1. List Coaches      |
            >| 2. Add Coach         |
            >------------------------
        """.trimMargin(">")
    )

}
