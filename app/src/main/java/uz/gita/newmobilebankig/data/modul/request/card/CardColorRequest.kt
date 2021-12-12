package uz.gita.newmobilebankig.data.modul.request.card

import com.google.gson.annotations.SerializedName

data class CardColorRequest(

	@field:SerializedName("color")
	val color: Int,

	@field:SerializedName("userCardId")
	val userCardId: Int
)
