package uz.gita.newmobilebankig.presentation.viewModels.auth.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uz.gita.newmobilebankig.data.ScreenState
import uz.gita.newmobilebankig.domen.usecases.auth.SplashScreenUseCase
import uz.gita.newmobilebankig.presentation.viewModels.auth.SplashScreenViewModel
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModelImpl @Inject constructor(private val useCase : SplashScreenUseCase) : ViewModel(), SplashScreenViewModel {
    override val openLoginScreenLiveData = MutableLiveData<Unit>()
    override val openMainScreenLiveData = MutableLiveData<Unit>()
    override val openIdenScreenLiveData = MutableLiveData<Unit>()

    init {
        viewModelScope.launch {
            delay(2000)
            when {
                useCase.startScreen() == ScreenState.MAIN.amount -> {
                    openMainScreenLiveData.value = Unit
                }
                useCase.startScreen() == ScreenState.PIN.amount -> {
                    openIdenScreenLiveData.value = Unit
                }
                else -> openLoginScreenLiveData.value = Unit
            }
        }
    }
}