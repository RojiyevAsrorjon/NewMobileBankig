package uz.gita.newmobilebankig.presentation.viewModels.auth.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobilebanking.utils.isConnected
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.newmobilebankig.data.modul.request.auth.LoginRequest
import uz.gita.newmobilebankig.domen.usecases.auth.LoginScreenUseCase
import uz.gita.newmobilebankig.presentation.viewModels.auth.AuthLoginScreenViewModel
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModelImpl @Inject constructor(private val loginScreenUseCase: LoginScreenUseCase) : ViewModel(), AuthLoginScreenViewModel {
    override val showProgressBarLiveData = MutableLiveData<Unit>()
    override val hideProgressBarLiveData = MutableLiveData<Unit>()
    override val enableContinueButtonLiveData = MutableLiveData<Unit>()
    override val disableContinueButtonLiveData = MutableLiveData<Unit>()
    override val openRegisterScreenLiveData = MutableLiveData<Unit>()
    override val openSmsVerifyScreenLiveData = MutableLiveData<String>()
    override val openResetPasswordScreenLiveData = MutableLiveData<Unit>()
    override val errorMessageLiveData = MutableLiveData<String>()

    override fun login(request: LoginRequest) {
        if (!isConnected()) {
            errorMessageLiveData.value = "Tarmoq bilan aloqa yo`q!"
            return
        }
        showProgressBarLiveData.value = Unit
        disableContinueButtonLiveData.value = Unit
        loginScreenUseCase.login(request).onEach {
            it.onSuccess {
                hideProgressBarLiveData.value = Unit
                enableContinueButtonLiveData.value = Unit
                openSmsVerifyScreenLiveData.value = request.phone
            }
            it.onFailure { throwable ->
                hideProgressBarLiveData.value = Unit
                enableContinueButtonLiveData.value = Unit
                errorMessageLiveData.value = throwable.message
            }
        }.launchIn(viewModelScope)
    }

    override fun openRegisterScreen() {
        openRegisterScreenLiveData.value = Unit
    }

    override fun openResetPasswordScreen() {
        openResetPasswordScreenLiveData.value = Unit
    }
}