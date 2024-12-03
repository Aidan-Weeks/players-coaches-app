
package ie.setu.controllers
import ie.setu.models.Coach
import ie.setu.models.Player
import ie.setu.persistence.Serializer
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
