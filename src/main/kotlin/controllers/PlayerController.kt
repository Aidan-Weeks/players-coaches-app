package ie.setu.controllers
import ie.setu.models.Player

class PlayerController {
    private var players = mutableListOf<Player>()
    private var lastId = 0
    private fun getId() = lastId++

    fun addPlayer(player: Player): Boolean {
        player.playerId = getId()
        return players.add(player)
    }

    fun listPlayers(): String =
        if (players.isEmpty()) {
            "No players in system"
        }else{
            formatListString(players.filter { players ->!players.isPlayerArchived })
        }
    fun numberOfPlayers() = players.size


    private fun formatListString(playersToFormat: List<Player>): String =
        playersToFormat
            .joinToString(separator = "\n") { player ->
                players.indexOf(player).toString() + ": " + player.toString()
            }
}

