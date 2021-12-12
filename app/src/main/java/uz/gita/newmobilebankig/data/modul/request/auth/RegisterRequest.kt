package uz.gita.newmobilebankig.data.modul.request.auth

data class RegisterRequest(

    val firstName: String,

    val lastName: String,

    val password: String,

    val phone: String,

    val status: Int = 0
)
