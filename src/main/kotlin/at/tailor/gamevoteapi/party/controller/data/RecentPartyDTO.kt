package at.tailor.gamevoteapi.party.controller.data

data class RecentPartyDTO(
    val code: String?,
    val status: String,
    val attendeeCount: Int,
    val beerCount: Int
)
