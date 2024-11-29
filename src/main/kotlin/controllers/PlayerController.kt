package ie.setu.controllers
import ie.setu.models.Player

class PlayerController {
    private var players = mutableListOf<Player>()
    private var lastId = 0
    private fun getId() = lastId++

    fun addPlayer(player: Player) {
        player.playerId = getId()
        player.name = players.add(player).toString()
    }

    fun listPlayers() = players


    fun numberOfPlayers() = players.size

    fun listAllPlayers(): String =
        if (players.isEmpty()) {
            "No players stored"
        } else {
            formatListString(players)
        }


    private fun formatListString(playersToFormat: List<Player>): String =
        playersToFormat
            .joinToString(separator = "\n") { player ->
                players.indexOf(player).toString() + ": " + player.toString()
            }
}

