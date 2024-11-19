package ie.setu.controllers
import ie.setu.models.Player

class PlayerController {
    private val players = mutableListOf<Player>()
    private var lastId = 0
    private fun getId() = lastId++

    fun addPlayer(player: Player){
        player.playerId = getId()
        players.add(player)
    }

    fun listPlayers() = players
}