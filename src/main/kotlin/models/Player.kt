package ie.setu.models

data class Player(
    var playerId: Int,
    var name: String,
    var phone: Int,
    var isPlayerArchived: Boolean
)