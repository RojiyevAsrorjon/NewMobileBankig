package uz.gita.newmobilebankig.data.modul.responses.card

import java.io.Serializable

data class GetAllCardsResponse(
	val data: Data
)

data class Data(
	val data: List<DataItem>
)

data class DataItem(
	val owner: String,
	val cardName: String,
	val balance: Int,
	val pan: String,
	val exp: String,
	val id : Int,
	val color : Int,
	val ignoreBalance : Boolean,
	val status: Int
) : Serializable

