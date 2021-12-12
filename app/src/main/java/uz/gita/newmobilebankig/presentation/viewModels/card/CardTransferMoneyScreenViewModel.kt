package uz.gita.newmobilebankig.presentation.viewModels.card

import androidx.lifecycle.LiveData
import uz.gita.newmobilebankig.data.modul.request.transferMoney.request.TransferMoneyRequest

interface CardTransferMoneyScreenViewModel {
    val showProgressBarLiveData: LiveData<Unit>
    val hideProgressBarLiveData: LiveData<Unit>

    val errorMessageLiveData: LiveData<String>

    val openTransferSuccessfulScreenLiveData: LiveData<Unit>

    val enableSendButtonLiveData: LiveData<Unit>
    val disableSendButtonLiveData : LiveData<Unit>

    fun transfer(request : TransferMoneyRequest)
}