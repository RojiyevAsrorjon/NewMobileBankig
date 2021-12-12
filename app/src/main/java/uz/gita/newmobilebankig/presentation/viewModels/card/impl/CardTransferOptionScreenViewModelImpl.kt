package uz.gita.newmobilebankig.presentation.viewModels.card.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobilebanking.utils.isConnected
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.newmobilebankig.data.modul.request.card.CardDeleteRequest
import uz.gita.newmobilebankig.data.modul.responses.card.DataItem
import uz.gita.newmobilebankig.domen.usecases.card.TransferMoneyOptionUseCase
import uz.gita.newmobilebankig.presentation.viewModels.card.CardTransferOptionScreenViewModel
import javax.inject.Inject

@HiltViewModel
class CardTransferOptionScreenViewModelImpl @Inject constructor(private val useCase: TransferMoneyOptionUseCase) : ViewModel(), CardTransferOptionScreenViewModel{
    override val showProgressBarLiveData = MutableLiveData<Unit>()
    override val hideProgressBarLiveData = MutableLiveData<Unit>()
    override val errorMessageLiveData = MutableLiveData<String>()
    override val openTransferScreenLiveData = MutableLiveData<DataItem>()
    override val enableButtonsLiveData = MutableLiveData<Unit>()
    override val disableButtonsLiveData = MutableLiveData<Unit>()

    override fun deleteCard(request: CardDeleteRequest) {
        if (!isConnected()) {
            errorMessageLiveData.value = "Tarmoq bilan aloqa yo`q!"
            return
        }
        showProgressBarLiveData.value = Unit
        disableButtonsLiveData.value = Unit
        useCase.deleteCard(request).onEach {
            hideProgressBarLiveData.value = Unit
            enableButtonsLiveData.value = Unit
            it.onSuccess {
                errorMessageLiveData.value = "Card deleted successfully!"
            }
            it.onFailure { throwable ->
                errorMessageLiveData.value = throwable.message
            }
        }.launchIn(viewModelScope)
    }

    override fun openFee() {
       errorMessageLiveData.value = "To`lov qilish imkoni yo`q!"
    }

    override fun openTransferScreen(dataItem: DataItem) {
        openTransferScreenLiveData.value = dataItem
    }
}