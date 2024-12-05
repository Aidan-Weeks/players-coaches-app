
package ie.setu.controllers
import ie.setu.models.Coach
import ie.setu.models.Player
import ie.setu.persistence.Serializer
import ie.setu.utils.isValidListIndex
import kotlin.jvm.Throws

class PlayerController(serializerType: Serializer) {
    private var serializer: Serializer = serializerType
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

    fun findPlayer(index: Int): Player? {
        return if (isValidListIndex(index, players)) {
            players[index]
        }else{
            null
        }
    }

    fun updatePlayer(playerToUpdate: Int, player: Player?): Boolean {
        val foundPlayer = findPlayer(playerToUpdate)

        if ((foundPlayer != null) && (player != null)) {
            foundPlayer.playerId = player.playerId
            foundPlayer.name = player.name
            foundPlayer.phone = player.phone
        }
        return true
    }

    fun isValidPlayer(index: Int): Boolean {
        return isValidListIndex(index, players)
    }

    private fun formatListString(playersToFormat: List<Player>): String =
        playersToFormat
            .joinToString(separator = "\n") { player ->
                players.indexOf(player).toString() + ": " + player.toString()
            }
    @Throws(Exception::class)
    fun load() {
        players = serializer.read() as ArrayList<Player>
    }

    @Throws(Exception::class)
    fun store(){
        serializer.write(players)
    }
}
