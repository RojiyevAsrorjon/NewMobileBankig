package uz.gita.newmobilebankig.presentation.viewModels.card.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobilebanking.utils.isConnected
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.newmobilebankig.data.modul.request.transferMoney.request.TransferMoneyRequest
import uz.gita.newmobilebankig.domen.usecases.card.TransferMoneyUseCase
import uz.gita.newmobilebankig.presentation.viewModels.card.CardTransferMoneyScreenViewModel
import javax.inject.Inject

@HiltViewModel
class CardTransferMoneyMoneyScreenViewModelImpl @Inject constructor(private val useCase : TransferMoneyUseCase) : ViewModel(), CardTransferMoneyScreenViewModel{
    override val showProgressBarLiveData = MutableLiveData<Unit>()
    override val hideProgressBarLiveData = MutableLiveData<Unit>()
    override val errorMessageLiveData = MutableLiveData<String>()
    override val openTransferSuccessfulScreenLiveData = MutableLiveData<Unit>()
    override val enableSendButtonLiveData = MutableLiveData<Unit>()
    override val disableSendButtonLiveData = MutableLiveData<Unit>()

    override fun transfer(request: TransferMoneyRequest) {
        if (!isConnected()) {
            errorMessageLiveData.value = "Tarmoq bilan aloqa yo`q!"
            return
        }
        showProgressBarLiveData.value = Unit
        disableSendButtonLiveData.value = Unit
        useCase.transfer(request).onEach {
            it.onSuccess {
                openTransferSuccessfulScreenLiveData.value = Unit
            }
            it.onFailure {
                errorMessageLiveData.value = it.message
            }
        }.launchIn(viewModelScope)
    }
}