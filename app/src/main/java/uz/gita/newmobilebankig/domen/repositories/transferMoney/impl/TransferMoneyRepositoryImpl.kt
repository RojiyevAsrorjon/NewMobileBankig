package uz.gita.newmobilebankig.domen.repositories.transferMoney.impl

import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import uz.gita.newmobilebankig.data.modul.ErrorData
import uz.gita.newmobilebankig.data.modul.request.transferMoney.request.TransferMoneyRequest
import uz.gita.newmobilebankig.data.modul.request.transferMoney.response.TransferMoneyData
import uz.gita.newmobilebankig.data.network.apies.CardTransferMoneyApi
import uz.gita.newmobilebankig.domen.repositories.transferMoney.TransferMoneyRepository
import javax.inject.Inject

class TransferMoneyRepositoryImpl @Inject constructor(private val api: CardTransferMoneyApi) : TransferMoneyRepository {
    override fun sendMoney(request: TransferMoneyRequest): Flow<Result<TransferMoneyData>> = flow {
        val response = api.sendMoney(request)
        if (response.isSuccessful) {
            emit(Result.success(response.body()!!.data))
        } else {
            var res = "dfs"
            response.errorBody()?.let {
                res = Gson().fromJson<ErrorData>(it.string(), ErrorData::class.java).message
            }
            emit(Result.failure(Throwable(res)))
        }

    }
}