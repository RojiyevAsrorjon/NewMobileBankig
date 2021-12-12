package uz.gita.newmobilebankig.presentation.viewModels.card

import androidx.lifecycle.LiveData
import uz.gita.newmobilebankig.data.modul.responses.card.CardVerifyResponse

interface CardConfirmScreenViewModel {
    val cardNameLiveData : LiveData<String>
    val cardPanLiveData : LiveData<String>
    val cardExpireLiveData : LiveData<String>
    val backButtonLiveData : LiveData<Unit>
    fun bindData(response : CardVerifyResponse)
    fun pressBackButton()
}