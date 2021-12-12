package uz.gita.newmobilebankig.presentation.viewModels.card

import androidx.lifecycle.LiveData
import uz.gita.newmobilebankig.data.modul.request.auth.RegisterRequest
import uz.gita.newmobilebankig.data.modul.request.card.AddCardRequest
import javax.inject.Inject

interface CardAddScreenViewModel {
    val showProgressBarLiveData : LiveData<Unit>
    val hideProgressBarLiveData : LiveData<Unit>

    val enableSaveButtonLiveData : LiveData<Unit>
    val disableSaveButtonLiveData : LiveData<Unit>

    val openCardSmsVerifyScreenLiveData : LiveData<String>

    val backButtonLiveData : LiveData<Unit>

    val errorMessageLiveData : LiveData<String>

    fun addCard(request : AddCardRequest)
}