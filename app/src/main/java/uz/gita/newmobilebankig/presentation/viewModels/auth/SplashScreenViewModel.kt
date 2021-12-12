package uz.gita.newmobilebankig.presentation.viewModels.auth

import androidx.lifecycle.LiveData

interface SplashScreenViewModel {
    val openLoginScreenLiveData : LiveData<Unit>
    val openMainScreenLiveData : LiveData<Unit>
    val openIdenScreenLiveData : LiveData<Unit>
}