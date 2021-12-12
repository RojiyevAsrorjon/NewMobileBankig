package uz.gita.newmobilebankig.domen.repositories.transferMoney

import kotlinx.coroutines.flow.Flow
import uz.gita.newmobilebankig.data.modul.request.transferMoney.request.TransferMoneyRequest
import uz.gita.newmobilebankig.data.modul.request.transferMoney.response.TransferMoneyData
import uz.gita.newmobilebankig.data.modul.request.transferMoney.response.TransferMoneyResponse

interface TransferMoneyRepository {

    fun sendMoney(request : TransferMoneyRequest) : Flow<Result<TransferMoneyData>>
}