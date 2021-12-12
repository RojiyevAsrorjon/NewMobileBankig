package uz.gita.newmobilebankig.presentation.viewModels.card

import androidx.lifecycle.LiveData
import uz.gita.newmobilebankig.data.modul.request.card.CardVerifyRequest

interface CardSmsVerifyViewModel {
    val showProgressBarLiveData : LiveData<Unit>
    val hideProgressBarLiveData : LiveData<Unit>

    val enableContinueButtonLiveData: LiveData<Unit>
    val disableContinueButtonLiveData : LiveData<Unit>

    val backButtonLiveData : LiveData<Unit>

    val errorMessageLiveData : LiveData<String>

    val openConfirmedCardScreenLiveData : LiveData<Unit>

    val openUnconfirmedScreenLiveData : LiveData<Unit>
    val timerObserver : LiveData<Int>
    fun verifyCard(request : CardVerifyRequest)
    fun openUnconfirmedScreen()
}