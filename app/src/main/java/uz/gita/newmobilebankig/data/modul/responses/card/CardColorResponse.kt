package uz.gita.newmobilebankig.data.modul.responses.card


data class CardColorResponse(

	val data: CardColorData
)

data class CardColorData(
	val color: Int,

	val userCardId: Int
)
