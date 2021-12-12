package uz.gita.newmobilebankig.presentation.viewModels.auth

import androidx.lifecycle.LiveData

interface AuthPinScreenViewModel {
    val enableContinueButtonLiveData : LiveData<Unit>
    val disableContinueButtonLiveData : LiveData<Unit>

    val enableEditTextLiveData : LiveData<Unit>
    val disableEditTextLiveData: LiveData<Unit>

    val openMainScreenLiveData : LiveData<Unit>

    fun setMainScreen()
    fun setPinScreen(pin: String)


    fun showEditTexts()
    fun hideEditTexts()
}