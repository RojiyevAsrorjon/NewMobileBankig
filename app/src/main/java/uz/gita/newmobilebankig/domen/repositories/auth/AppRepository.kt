package uz.gita.newmobilebankig.domen.repositories.auth

import uz.gita.newmobilebankig.data.ScreenState

interface AppRepository {
    fun startScreen() : Int
    fun setStartScreen(screen : Int)
    fun getToken() : String
    fun setPin(pin : String)
}