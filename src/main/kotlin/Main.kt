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
import kotlin.system.exitProcess


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
            3 -> if(coachVerify()){
                teamMenu()
            }else println("No coaches in system")
            8 -> save()
            9 -> load()
            0 -> exit()

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
                >| 5. Back              |
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
        }else println("No coaches in system")
        4 -> if(coachVerify()){
            removeCoach()
        }else println("No coaches in System")
        5 -> back()
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

fun removeCoach() {
    listAllCoaches()
        val indexToDelete = readNextInt("Enter the Coach ID to Remove: \n")
        val coachToDelete = coachController.deleteCoach(indexToDelete)
        if (coachToDelete != null) {
            println("Delete successful! Removed Coach: ${coachToDelete.name}")
        } else {
            println("Delete Failed")
        }
}

fun coachVerify(): Boolean{
    return coachController.numberOfCoaches() > 0
}

fun playerMenu() {

    val input = readNextInt(
        """
             >----------------------------
             >| 1. Add Player            |
             >| 2. List Players          |
             >| 3. Update Player info    |
             >| 4. Remove Player         |
             >| 5. Back                  |
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
        5 -> back()
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
                println("There are no players with player ID: ${playerToUpdate}")
            }
        }

fun removePlayer(){
    listAllPlayers()
        val indexToDelete = readNextInt("Enter the Player ID to Remove: \n")
        val playerToDelete = playerController.deletePlayer(indexToDelete)
        if(playerToDelete != null) {
            println("Delete successful! Removed Player: ${playerToDelete.name}")
        } else {
            println("Delete NOT successful")
        }
    }

fun playerVerify(): Boolean{
   return playerController.numberOfPlayers() > 0
}

fun teamMenu(){
    val input = readNextInt(
        """
                >----------------------------
                >| 1. Add Team              |
                >| 2. Add Player to Team    |
                >| 3. Remove player         |
                >| 4. List all Teams        |
                >| 5. List team Details     |
                >| 6. Update Team           |
                >| 7. Delete Team           |
                >| 8. Back                  |
                >----------------------------
                >   ===>
        """.trimMargin(">")
    )
    when(input){
        1->addTeam()
        2-> if (teamVerify()){
            addPlayerToTeam()
        }else println("No teams in system")
        3-> if (teamVerify()){
            removePlayerFromTeam()
        }else println("No teams in system")
        4-> if(teamVerify()){
            listAllTeams()
        }else println("No teams in system")
        5 -> if(teamVerify()){
            teamDetails()
        }else println("No teams in system")
        6 -> if(teamVerify()){
            updateTeam()
        }else println("No teams in system")
        7-> if(teamVerify()){
            removeTeam()
        }else println("No teams in system")
        8-> back()
    }
}

fun addTeam(){
    val teamName = readNextLine("Enter Team Name: \n")
    listAllCoaches()

    val teamCoachId = readNextInt("Enter the Team's coach ID: ")
    val teamCoach = coachController.findCoach(teamCoachId)
    if (teamCoach != null) {
       val isAdded = teamController.addTeam(Team(0, teamName, mutableListOf(), teamCoach, false))

        if (isAdded){
            println("Added team Successfully")
        }else{
            println("Failed to Add team")
        }
    }else{
        println("-----------------")
    }
}

fun listAllTeams(){
    println(teamController.listTeams())
}

fun addPlayerToTeam() {
    println(playerController.listPlayers())
    val playerId = readNextInt("Choose a playerId: ")
    val player = playerController.findPlayer(playerId)

    if (player != null) {
        println(teamController.listTeams())
        val teamId = readNextInt("Choose a teamId: ")
        if (teamController.isValidTeam(teamId)) {
            if (teamController.addPlayerToTeam(player, teamId)) {
                println("Player added successfully!")
            }else {
                println("Failed to add player to Team")
            }
        } else {
            println("There are no teams with team ID: $teamId")
        }
    }
}

fun removePlayerFromTeam() {
    listAllTeams()
    val teamId = readNextInt("Enter the TeamID to remove a player from: ")
    val team = teamController.findTeam(teamId)
    if (team != null) {
        if (team.player.isEmpty()) {
            println("No players on this team")
        }else {
            println("Players in ${team.teamName}:")
            team.player.forEach { println("ID: ${it.playerId} name: ${it.name}") }
            val playerId = readNextInt("Enter Player ID to remove: ")
            if (teamController.removePlayerFromTeam(playerId, teamId)) {
                println("Player removed successfully")
            } else {
                println("Failed to remove player from Team")
            }
        }
        }
}

fun teamDetails() {
        println(teamController.listTeams())
        val teamId = readNextInt("Enter a team ID: \n")
        val team = teamController.findTeam(teamId)

        if (team == null) {
            println("Invalid team \n --------------")
        } else {
            println("${team.teamName} ")
            if (team.player.isEmpty()){
                println("No players on ${team.teamName}")
            }else{
                println("Players ")
                team.player.forEach { player -> println(player.name)}
            }
        }
    }

fun updateTeam(){
    listAllTeams()
    val teamToUpdate = readNextInt("Enter a team ID: \n")
    val team = teamController.findTeam(teamToUpdate)
    if (team == null) {
        println("Invalid team \n --------------")
    } else {
        val teamName = readNextLine("Enter New team name: \n")
        println(coachController.listCoaches())
        val coachId = readNextInt("Enter New Coach ID: \n")
        val coach = coachController.findCoach(coachId)
            if (coach != null){
                teamController.updateTeam(teamToUpdate,teamName, coach)
                println("Successfully updated team: ${teamName}")
            }else println("Incorrect coach ID entered")
    }
}

fun removeTeam(){
    listAllTeams()
    val indexToDelete = readNextInt("Enter the ID of the team to remove: \n")
    val teamToDelete = teamController.deleteTeam(indexToDelete)
    if (teamToDelete != null) {
        println("Delete successful! Removed Team: ${teamToDelete.teamName}")
    }else{
        println("Delete Failed")
    }

}

fun teamVerify(): Boolean{
    return teamController.numberOfTeams() > 0
}


fun back(){
    println("-----------------")
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

fun exit(){
   println("Exiting app - Goodbye!")
   exitProcess(0)
}