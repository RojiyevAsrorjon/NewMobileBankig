package uz.gita.newmobilebankig.presentation.viewModels.card.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobilebanking.utils.isConnected
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.newmobilebankig.data.modul.request.card.CardVerifyRequest
import uz.gita.newmobilebankig.domen.usecases.card.CardSmsVerifyUseCase
import uz.gita.newmobilebankig.presentation.viewModels.card.CardSmsVerifyViewModel
import javax.inject.Inject

@HiltViewModel
class CardSmsVerifyScreenViewModelImpl @Inject constructor(private val useCase: CardSmsVerifyUseCase) : ViewModel(), CardSmsVerifyViewModel {
    override val showProgressBarLiveData = MutableLiveData<Unit>()
    override val hideProgressBarLiveData = MutableLiveData<Unit>()
    override val enableContinueButtonLiveData = MutableLiveData<Unit>()
    override val disableContinueButtonLiveData = MutableLiveData<Unit>()
    override val backButtonLiveData = MutableLiveData<Unit>()
    override val errorMessageLiveData = MutableLiveData<String>()
    override val openConfirmedCardScreenLiveData = MutableLiveData<Unit>()
    override val openUnconfirmedScreenLiveData = MutableLiveData<Unit>()
    override val timerObserver = MutableLiveData<Int>()

    init {
        timerObserver.value = 90
        disableContinueButtonLiveData.value = Unit
    }

    override fun verifyCard(request: CardVerifyRequest) {
        if (!isConnected()) {
            errorMessageLiveData.value = "Tarmoq bilan aloqa yo`q!"
            return
        }
        showProgressBarLiveData.value = Unit
        disableContinueButtonLiveData.value = Unit
        useCase.verify(request).onEach {
            hideProgressBarLiveData.value = Unit
            enableContinueButtonLiveData.value = Unit
            it.onSuccess {
                openConfirmedCardScreenLiveData.value = Unit
            }
            it.onFailure { throwable ->
                errorMessageLiveData.value = throwable.message
            }
        }.launchIn(viewModelScope)
    }

    override fun openUnconfirmedScreen() {
        openUnconfirmedScreenLiveData.value = Unit
    }
}