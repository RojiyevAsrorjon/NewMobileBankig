package uz.gita.newmobilebankig.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import uz.gita.newmobilebankig.data.database.LocalDatabase

@HiltAndroidApp
class App : Application() {
    companion object{
        lateinit var instance : App
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        LocalDatabase.init(this)
    }
}