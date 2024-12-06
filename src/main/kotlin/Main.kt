@file:Suppress("UNUSED_EXPRESSION")

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
                >| 1. Add Coach         |
                >| 2. List Coaches      |
                >| 3. Update Coach      |
                >| 4. Remove Coach      |
                >------------------------
                > ===>""".trimMargin(">")

    )
    when(input) {
        1 -> createCoach()
        2 -> if (coachVerify()){
            listAllCoaches()
        }else println("No Coaches in system")
        3 -> if (coachVerify()){
            updateCoach()
        }else println("No players in system")
        4 -> if(coachVerify()){
            removeCoach()
        }else println("No coaches in System")
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

fun updateCoach(){
    listAllCoaches()
    if (!coachVerify()){
        val coachToUpdate = readNextInt("Enter the coach ID: \n")
        if (coachController.isValidCoach(coachToUpdate)) {
            val name = readNextLine("Enter the coaches name: ")
            val phone = readNextInt("Enter ${name}'s phone number: ")

            if(coachController.updateCoach(coachToUpdate, Coach(coachToUpdate, name, phone, false))) {
                println("Update Successful")
            } else {
                println("Update Failed")
            }
        }else {
            println("There are no coaches with coach ID: ${coachToUpdate}")
        }
    }
}

fun removeCoach() {
    listAllCoaches()
    if (!coachVerify()){
        val indexToDelete = readNextInt("Enter the Coach ID to Remove: \n")
        val coachToDelete = coachController.deleteCoach(indexToDelete)
        if (coachToDelete != null) {
            println("Delete successful! Removed Coach: ${coachToDelete.name}")
        } else {
            println("Delete NOT successful")
        }
    } else {
        println("-----------------")
    }
}


fun coachVerify(): Boolean{
    return playerController.numberOfPlayers() == 0
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
        listAllPlayers()
        if (!playerVerify()){
            val playerToUpdate = readNextInt("Enter the player ID: \n")
            if (playerController.isValidPlayer(playerToUpdate)) {
                val name = readNextLine("Enter the players name: ")
                val phone = readNextInt("Enter ${name}'s phone number: ")

                if(playerController.updatePlayer(playerToUpdate, Player(playerToUpdate, name, phone, false))) {
                    println("Update Successful")
                } else {
                    println("Update Failed")
                }
            }else {
                println("There are no coaches with coach ID: ${playerToUpdate}")
            }
        }

}

fun removePlayer(){
    listAllPlayers()
    if(!playerVerify()){
        val indexToDelete = readNextInt("Enter the Player ID to Remove: \n")
        val playerToDelete = playerController.deletePlayer(indexToDelete)
        if(playerToDelete != null) {
            println("Delete successful! Removed Player: ${playerToDelete.name}")
        } else {
            println("Delete NOT successful")
        }
    }
}

fun playerVerify(): Boolean{
   return playerController.numberOfPlayers() > 0
}

fun teamMenu(){

}

/*fun addPlayerToTeam() {

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
}*/

/*fun teamList() {
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
}*/

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