package uz.gita.newmobilebankig.presentation.viewModels.card.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import uz.gita.newmobilebankig.data.modul.responses.card.CardVerifyResponse
import uz.gita.newmobilebankig.presentation.viewModels.card.CardConfirmScreenViewModel
import javax.inject.Inject

@HiltViewModel
class CardConfirmScreenViewModelImpl @Inject constructor() : ViewModel(), CardConfirmScreenViewModel{
    override val cardNameLiveData = MutableLiveData<String>()
    override val cardPanLiveData = MutableLiveData<String>()
    override val cardExpireLiveData = MutableLiveData<String>()
    override val backButtonLiveData = MutableLiveData<Unit>()
    override fun bindData(response: CardVerifyResponse) {
        cardNameLiveData.value = response.data.cardName
        cardExpireLiveData.value = response.data.exp
        cardPanLiveData.value = response.data.pan
    }

    override fun pressBackButton() {
        backButtonLiveData.value = Unit
    }
}