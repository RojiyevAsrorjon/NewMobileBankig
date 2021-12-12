package uz.gita.newmobilebankig.data.modul.responses.auth

data class VerifyResponse(
	val data: DataClass
)

data class DataClass(

	val access_token: String,
	val refresh_token: String
)

