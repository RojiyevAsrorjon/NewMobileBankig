package uz.gita.newmobilebankig.data.modul.responses.card

data class CardIgnoreBalanceResponse(
	val data: CardIgnoreBalanceData
)

data class CardIgnoreBalanceData(
	val userCardId: Int,
	val ignoreBalance: Boolean
)

