package uz.gita.newmobilebankig.presentation.viewModels.auth

import androidx.lifecycle.LiveData
import uz.gita.newmobilebankig.data.modul.request.auth.AuthVerifyRequest

interface AuthSmsVerifyScreenViewModel {
    val showProgressBarLiveData: LiveData<Unit>
    val hideProgressBarLiveData : LiveData<Unit>

    val enableContinueButtonLiveData : LiveData<Unit>
    val disableContinueButtonLiveData : LiveData<Unit>

    val openPinOptionScreenLiveData: LiveData<Unit>

    val timerLiveData : LiveData<Int>

    val errorMessageLiveData : LiveData<String>

    val enableResendButtonLiveData : LiveData<Unit>
    val disableResendButtonLiveData : LiveData<Unit>

    fun resend()

    fun verify(request : AuthVerifyRequest)

    fun resetTimer(time : Int)

    fun enableResendButton()


}