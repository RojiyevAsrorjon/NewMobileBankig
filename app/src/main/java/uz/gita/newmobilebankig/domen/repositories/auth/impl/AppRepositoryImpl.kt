package uz.gita.newmobilebankig.domen.repositories.auth.impl

import uz.gita.newmobilebankig.data.database.LocalDatabase
import uz.gita.newmobilebankig.domen.repositories.auth.AppRepository
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(val pref : LocalDatabase) : AppRepository {
    override fun startScreen() : Int = pref.screenState

    override fun setStartScreen(screen: Int) {
        pref.screenState = screen
    }

    override fun getToken(): String = pref.accessToken
    override fun setPin(pin: String) {
        pref.pin = pin
    }
}