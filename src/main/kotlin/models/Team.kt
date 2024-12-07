package ie.setu.models

data class Team(
    var teamId: Int = 0,
    var teamName: String = "",
    var player: MutableList<Player> = mutableListOf(),
    val coach: Coach,
    val isTeamArchived: Boolean
)