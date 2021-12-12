package uz.gita.newmobilebankig.presentation.viewModels.card.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobilebanking.utils.isConnected
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.newmobilebankig.data.modul.request.card.AddCardRequest
import uz.gita.newmobilebankig.domen.usecases.card.AddCardScreenUseCase
import uz.gita.newmobilebankig.presentation.viewModels.card.CardAddScreenViewModel
import javax.inject.Inject

@HiltViewModel
class CardAddScreenViewModelImpl @Inject constructor(private val useCase: AddCardScreenUseCase) : ViewModel(), CardAddScreenViewModel {
    override val showProgressBarLiveData = MutableLiveData<Unit>()
    override val hideProgressBarLiveData = MutableLiveData<Unit>()
    override val enableSaveButtonLiveData = MutableLiveData<Unit>()
    override val disableSaveButtonLiveData = MutableLiveData<Unit>()
    override val openCardSmsVerifyScreenLiveData=MutableLiveData<String>()
    override val backButtonLiveData = MutableLiveData<Unit>()
    override val errorMessageLiveData=MutableLiveData<String>()

    override fun addCard(request: AddCardRequest) {
        if (!isConnected()) {
            errorMessageLiveData.value = "Tarmoq bilan aloqa yo`q!"
            return
        }
        showProgressBarLiveData.value = Unit
        disableSaveButtonLiveData.value = Unit
        useCase.addCard(request).onEach {
            it.onSuccess {
                hideProgressBarLiveData.value = Unit
                enableSaveButtonLiveData.value = Unit
                openCardSmsVerifyScreenLiveData.value = request.pan
            }
            it.onFailure { throwable ->
                hideProgressBarLiveData.value = Unit
                enableSaveButtonLiveData.value = Unit
                errorMessageLiveData.value = throwable.message
            }
        }.launchIn(viewModelScope)
    }
}