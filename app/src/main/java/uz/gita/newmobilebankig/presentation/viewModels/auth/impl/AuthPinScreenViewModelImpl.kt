package uz.gita.newmobilebankig.presentation.viewModels.auth.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import uz.gita.newmobilebankig.domen.usecases.auth.PinScreenUseCase
import uz.gita.newmobilebankig.presentation.viewModels.auth.AuthPinScreenViewModel
import javax.inject.Inject

@HiltViewModel
class AuthPinScreenViewModelImpl @Inject constructor(private val pinScreenUseCase: PinScreenUseCase) : AuthPinScreenViewModel, ViewModel() {
    override val enableContinueButtonLiveData = MutableLiveData<Unit>()
    override val disableContinueButtonLiveData = MutableLiveData<Unit>()
    override val enableEditTextLiveData = MutableLiveData<Unit>()
    override val disableEditTextLiveData = MutableLiveData<Unit>()
    override val openMainScreenLiveData = MutableLiveData<Unit>()

    override fun setMainScreen() {
        openMainScreenLiveData.value = Unit
        pinScreenUseCase.setMainScreen()
    }

    override fun setPinScreen(pin: String) {
        openMainScreenLiveData.value = Unit
        pinScreenUseCase.setPinScreen()
        pinScreenUseCase.setPin(pin)
    }


    override fun showEditTexts() {
        enableEditTextLiveData.value = Unit
    }

    override fun hideEditTexts() {
        disableEditTextLiveData.value = Unit

    }
}