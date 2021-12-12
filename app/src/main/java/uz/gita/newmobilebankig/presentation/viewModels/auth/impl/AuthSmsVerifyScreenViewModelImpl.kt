package uz.gita.newmobilebankig.presentation.viewModels.auth.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobilebanking.utils.isConnected
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.newmobilebankig.data.modul.request.auth.AuthVerifyRequest
import uz.gita.newmobilebankig.domen.usecases.auth.AuthSmsVerifyScreenUseCase
import uz.gita.newmobilebankig.presentation.viewModels.auth.AuthSmsVerifyScreenViewModel
import javax.inject.Inject

@HiltViewModel
class AuthSmsVerifyScreenViewModelImpl @Inject constructor(private val authSmsVerifyScreenUseCase: AuthSmsVerifyScreenUseCase) : ViewModel(), AuthSmsVerifyScreenViewModel {

    override val showProgressBarLiveData = MutableLiveData<Unit>()
    override val hideProgressBarLiveData = MutableLiveData<Unit>()
    override val enableContinueButtonLiveData = MutableLiveData<Unit>()
    override val disableContinueButtonLiveData = MutableLiveData<Unit>()
    override val openPinOptionScreenLiveData = MutableLiveData<Unit>()
    override val timerLiveData = MutableLiveData<Int>()
    override val errorMessageLiveData = MutableLiveData<String>()
    override val enableResendButtonLiveData = MutableLiveData<Unit>()
    override val disableResendButtonLiveData = MutableLiveData<Unit>()

    init {
        timerLiveData.value = 90
        disableResendButtonLiveData.value = Unit
    }

    override fun resend() {
        if (!isConnected()) {
            errorMessageLiveData.value = "Taqmoq bilan aloqa yo`q"
            return
        }
        showProgressBarLiveData.value = Unit
        disableContinueButtonLiveData.value = Unit
        authSmsVerifyScreenUseCase.resend().onEach {
            it.onSuccess {
                hideProgressBarLiveData.value = Unit
                enableContinueButtonLiveData.value = Unit
                disableResendButtonLiveData.value = Unit
            }
            it.onFailure { throwable ->
                hideProgressBarLiveData.value = Unit
                enableContinueButtonLiveData.value = Unit
                errorMessageLiveData.value = throwable.message
            }
        }.launchIn(viewModelScope)
    }

    override fun verify(request: AuthVerifyRequest) {
        if (!isConnected()) {
            errorMessageLiveData.value = "Tarmoq bilan aloqa yo`q"
            return
        }
        showProgressBarLiveData.value = Unit
        disableContinueButtonLiveData.value = Unit
        authSmsVerifyScreenUseCase.verify(request).onEach {
            it.onSuccess {
                hideProgressBarLiveData.value = Unit
                enableContinueButtonLiveData.value = Unit
                openPinOptionScreenLiveData.value = Unit
            }
            it.onFailure { throwable ->
                hideProgressBarLiveData.value = Unit
                enableContinueButtonLiveData.value = Unit
                errorMessageLiveData.value = throwable.message
            }
        }.launchIn(viewModelScope)

    }

    override fun resetTimer(time: Int) {
        timerLiveData.value = 90
        disableResendButtonLiveData.value = Unit
    }

    override fun enableResendButton() {
        enableResendButtonLiveData.value = Unit
    }
}