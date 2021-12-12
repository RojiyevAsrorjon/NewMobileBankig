package uz.gita.newmobilebankig.domen.usecases.auth.impl

import uz.gita.newmobilebankig.data.ScreenState
import uz.gita.newmobilebankig.domen.repositories.auth.AppRepository
import uz.gita.newmobilebankig.domen.usecases.auth.PinScreenUseCase
import javax.inject.Inject

class PinScreenUseCaseImpl @Inject constructor(private val repository: AppRepository) : PinScreenUseCase {
    override fun setPinScreen() {
        repository.setStartScreen(ScreenState.PIN.amount)
    }

    override fun setPin(pin: String) {
        repository.setPin(pin)
    }

    override fun setMainScreen() {
        repository.setStartScreen(ScreenState.MAIN.amount)
    }
}