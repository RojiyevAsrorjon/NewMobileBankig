package uz.gita.newmobilebankig.presentation.viewModels.card

import androidx.lifecycle.LiveData
import uz.gita.newmobilebankig.data.modul.responses.card.DataItem

interface CardMainScreenViewModel {
    val showProgressBarLiveData : LiveData<Unit>
    val hideProgressBarLiveData : LiveData<Unit>

    val cardsListLiveData : LiveData<List<DataItem>>

    val openAddCardLiveData : LiveData<Unit>

    val openTransferOptionScreenLiveData : LiveData<DataItem>

    val errorMessageLiveData : LiveData<String>

    fun openMainScreen()

    fun openTransferOptionScreen(dataItem: DataItem)
}