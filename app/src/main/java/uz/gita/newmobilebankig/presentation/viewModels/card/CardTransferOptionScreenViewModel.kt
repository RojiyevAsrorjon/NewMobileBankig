package uz.gita.newmobilebankig.presentation.viewModels.card

import androidx.lifecycle.LiveData
import uz.gita.newmobilebankig.data.modul.request.card.CardDeleteRequest
import uz.gita.newmobilebankig.data.modul.responses.card.DataItem

interface CardTransferOptionScreenViewModel {
    val showProgressBarLiveData: LiveData<Unit>
    val hideProgressBarLiveData: LiveData<Unit>
    val errorMessageLiveData: LiveData<String>
    val openTransferScreenLiveData: LiveData<DataItem>

    val enableButtonsLiveData: LiveData<Unit>
    val disableButtonsLiveData: LiveData<Unit>

    fun deleteCard(request: CardDeleteRequest)
    fun openFee()
    fun openTransferScreen(dataItem: DataItem)
}