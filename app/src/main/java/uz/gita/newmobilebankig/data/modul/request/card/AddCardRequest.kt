package uz.gita.newmobilebankig.data.modul.request.card

import java.io.Serializable
import kotlin.String

data class AddCardRequest(
    val cardName: String,
    val pan: String,
    val exp: String
) : Serializable

