package ie.setu.models

data class Team(
    var teamId: Int = 0,
    var teamName: String = "",
    var player: MutableList<Player>,
    val coachId: Int
)