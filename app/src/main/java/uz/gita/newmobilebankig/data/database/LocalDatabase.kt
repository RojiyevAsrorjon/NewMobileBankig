package uz.gita.newmobilebankig.data.database

import android.content.Context
import com.example.mobilebanking.utils.IntPreference
import com.example.mobilebanking.utils.StringPreference
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDatabase @Inject constructor(@ApplicationContext context: Context){

    companion object{
        private lateinit var instance : LocalDatabase
        fun init(context: Context) {
            instance = LocalDatabase(context)
        }

        fun getInstance() = instance
    }

    private val sharedPref = context.getSharedPreferences("my_mobile_banking", Context.MODE_PRIVATE)

    var phoneNumber : String by StringPreference(sharedPref)
    var pin : String by StringPreference(sharedPref)

    var accessToken : String by StringPreference(sharedPref)
    var refreshToken : String by StringPreference(sharedPref)

    var screenState : Int by IntPreference(sharedPref)
}