
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
import persistence.JSONSerializer
import persistence.XMLSerializer
import java.io.File


val logger = KotlinLogging.logger {}
private val coachController = CoachController(JSONSerializer(File("coaches.json")))
private val playerController = PlayerController(JSONSerializer(File("players.json")))
private val teamController = TeamController(JSONSerializer(File("team.json")))

//private val coachController = CoachController(XMLSerializer(File("coaches.xml")))
//private val playerController = PlayerController(XMLSerializer(File("players.xml")))
//private val teamController = TeamController(XMLSerializer(File("team.xml")))
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
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
          > |   3. Team Options             |
          > ---------------------------------
          > |   8. Save Changes             |
          > |   9. Load                     |
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
            3 -> teamMenu()
            8 -> save()
            9 -> load()
            else -> println("Invalid value: $input")
        }
    }while(true)
}

fun coachMenu(){
    logger.info { "Launching Coach Menu \n" }
    val input = readNextInt(
        """
                >------------------------
                >| 1. List Coaches      |
                >| 2. Add Coach         |
                >| 3. Update Coach      |
                >| 4. Remove Coach      |
                >------------------------
                > ===>""".trimMargin(">")
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

fun playerMenu() {

    val input = readNextInt(
        """
                >----------------------------
                >| 1. Add Player            |
                >| 2. List Players          |
                >| 3. Update Player info    |
                >| 4. Remove Player         |
                >----------------------------
                >   ===>
            """.trimMargin(">")
    )
    when (input) {
        1 -> createPlayer()
        2 -> if (playerVerify()){
        listAllPlayers()
        }else println("No players in system")
        3 -> if (playerVerify()){
        updatePlayer()
        }else println("No players in system")
        4 -> if (playerVerify()){
            removePlayer()
        }else println("No players in system")
    }
}

fun listAllPlayers(){
    println(playerController.listPlayers())
}

fun createPlayer(){
    val playerName = readNextLine("Enter a players name: ")
    val playerNumber = readNextInt("Enter the players number: ")

    playerController.addPlayer(Player(0, playerName, playerNumber, false))
}

fun updatePlayer(){

}

fun removePlayer(){

}

fun playerVerify(): Boolean{
   return playerController.numberOfPlayers() > 0
}

fun teamMenu(){

}

fun addPlayerToTeam() {
    if (playerController.numberOfPlayers() == 0) {
        println("No players in system")
    } else if (coachController.numberOfCoaches() == 0) {
        println("No coaches in system")
    } else {
        println(playerController.listPlayers())
        val playerId = readNextInt("Choose a playerId: ")
        println(coachController.listCoaches())
        val coachId = readNextInt("Choose a coachId: ")

        val isAdded = teamController.addPlayerToTeam(playerId, coachId)

        if(isAdded){
            println("Added Successfully")
        }else{
            println("Add Failed")
        }
    }
}
fun teamList() {
    if (playerController.numberOfPlayers() == 0) {
        println("No players in system")
    } else if (coachController.numberOfCoaches() == 0) {
        println("No coaches in system")
    } else {
        println(coachController.listCoaches())
        val coachName = readNextLine("Enter a coach Name: \n")
        val players = coachController.findCoachName(coachName)

        if (players.isEmpty()) {
            println("No players assigned to this coach \n")
        } else {
            println("$coachName's Team: ")
            players.forEach {
                println("Player name: ${it.name}")
            }
        }
    }
}
fun save(){
    try {
        coachController.store()
        playerController.store()
        teamController.store()
    }catch (e: Exception) {
        System.err.println("Error writing to file: $e")
    }
}

fun load(){
    try {
        coachController.load()
        playerController.load()
        teamController.load()
    } catch (e: Exception) {
        System.err.println("Error reading from file: $e")
    }
}