package uz.gita.newmobilebankig.data.modul.responses.card

data class CardVerifyResponse(
	val data: CardVerifyData
)

data class CardVerifyData(
	val owner: String,
	val cardName: String,
	val balance: Int,
	val color: Int,
	val id: Int,
	val pan: String,
	val exp: String,
	val ignoreBalance: Boolean,
	val status: Int
)

