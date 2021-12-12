package uz.gita.newmobilebankig.presentation.viewModels.auth

import androidx.lifecycle.LiveData
import uz.gita.newmobilebankig.data.modul.request.auth.LoginRequest

interface AuthLoginScreenViewModel {
    val showProgressBarLiveData : LiveData<Unit>
    val hideProgressBarLiveData : LiveData<Unit>

    val enableContinueButtonLiveData : LiveData<Unit>
    val disableContinueButtonLiveData : LiveData<Unit>

    val openRegisterScreenLiveData : LiveData<Unit>
    val openSmsVerifyScreenLiveData : LiveData<String>
    val openResetPasswordScreenLiveData : LiveData<Unit>

    val errorMessageLiveData : LiveData<String>

    fun login(request : LoginRequest)

    fun openRegisterScreen()
    fun openResetPasswordScreen()
}