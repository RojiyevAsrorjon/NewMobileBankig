package uz.gita.newmobilebankig.domen.usecases.card

import android.view.translation.TranslationResponse
import kotlinx.coroutines.flow.Flow
import uz.gita.newmobilebankig.data.modul.request.transferMoney.request.TransferMoneyRequest
import uz.gita.newmobilebankig.data.modul.request.transferMoney.response.TransferMoneyData

interface TransferMoneyUseCase {
    fun transfer(request : TransferMoneyRequest) : Flow<Result<TransferMoneyData>>
}