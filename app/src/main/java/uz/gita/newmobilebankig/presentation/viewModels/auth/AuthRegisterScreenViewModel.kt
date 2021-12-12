package uz.gita.newmobilebankig.presentation.viewModels.auth

import androidx.lifecycle.LiveData
import uz.gita.newmobilebankig.data.modul.request.auth.RegisterRequest

interface AuthRegisterScreenViewModel {
    val showProgressBarLiveData : LiveData<Unit>
    val hideProgressBarLiveData : LiveData<Unit>

    val enableSaveButtonLiveData : LiveData<Unit>
    val disableSaveButtonLiveData : LiveData<Unit>

    val openSmsVerifyScreenLiveData : LiveData<String>

    val backButtonLiveData : LiveData<Unit>

    val errorMessageLiveData : LiveData<String>

    fun saveData(request : RegisterRequest)
}