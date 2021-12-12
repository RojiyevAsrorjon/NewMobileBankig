package uz.gita.newmobilebankig.presentation.viewModels.auth.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobilebanking.utils.isConnected
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.newmobilebankig.data.modul.request.auth.RegisterRequest
import uz.gita.newmobilebankig.domen.usecases.auth.RegisterScreenUseCase
import uz.gita.newmobilebankig.presentation.viewModels.auth.AuthRegisterScreenViewModel
import javax.inject.Inject


@HiltViewModel
class AuthRegisterScreenViewModelImpl @Inject constructor(private val registerScreenUseCase: RegisterScreenUseCase) : ViewModel(), AuthRegisterScreenViewModel {
    override val showProgressBarLiveData = MutableLiveData<Unit>()
    override val hideProgressBarLiveData = MutableLiveData<Unit>()
    override val enableSaveButtonLiveData = MutableLiveData<Unit>()
    override val disableSaveButtonLiveData = MutableLiveData<Unit>()
    override val openSmsVerifyScreenLiveData = MutableLiveData<String>()
    override val backButtonLiveData = MutableLiveData<Unit>()
    override val errorMessageLiveData = MutableLiveData<String>()

    override fun saveData(request: RegisterRequest) {
        if (!isConnected()) {
            errorMessageLiveData.value = "Taqmoq bilan aloqa yo`q!"
            return
        }
        showProgressBarLiveData.value = Unit
        disableSaveButtonLiveData.value = Unit
        registerScreenUseCase.register(request).onEach {

            it.onSuccess {
                openSmsVerifyScreenLiveData.value = request.phone
                hideProgressBarLiveData.value = Unit
                enableSaveButtonLiveData.value = Unit
                registerScreenUseCase.saveData(request.phone, request.password)
            }
            it.onFailure { throwable ->
                hideProgressBarLiveData.value = Unit
                enableSaveButtonLiveData.value = Unit
                errorMessageLiveData.value = throwable.message
            }
        }.launchIn(viewModelScope)
    }


}