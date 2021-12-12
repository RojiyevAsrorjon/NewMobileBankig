package uz.gita.newmobilebankig.presentation.viewModels.card.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.newmobilebankig.data.modul.responses.card.DataItem
import uz.gita.newmobilebankig.domen.usecases.card.MainScreenUseCase
import uz.gita.newmobilebankig.presentation.viewModels.card.CardMainScreenViewModel
import javax.inject.Inject

@HiltViewModel
class CardMainScreenViewModelImpl @Inject constructor(private val useCase: MainScreenUseCase) : ViewModel(), CardMainScreenViewModel {
    override val showProgressBarLiveData = MutableLiveData<Unit>()
    override val hideProgressBarLiveData = MutableLiveData<Unit>()
    override val cardsListLiveData = MutableLiveData<List<DataItem>>()
    override val openAddCardLiveData = MutableLiveData<Unit>()
    override val openTransferOptionScreenLiveData = MutableLiveData<DataItem>()
    override val errorMessageLiveData = MutableLiveData<String>()
    override fun openMainScreen() {
        openAddCardLiveData.value = Unit
    }

    init {
        showProgressBarLiveData.value = Unit
        useCase.getAllCards().onEach {
            it.onSuccess {
                hideProgressBarLiveData.value = Unit
                cardsListLiveData.value = it
            }
            it.onFailure { throwable ->
                hideProgressBarLiveData.value = Unit
                errorMessageLiveData.value = throwable.message
            }
        }.launchIn(viewModelScope)
    }

    override fun openTransferOptionScreen(dataItem: DataItem) {
        openTransferOptionScreenLiveData.value = dataItem
    }
}