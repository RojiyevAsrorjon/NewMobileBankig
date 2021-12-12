package uz.gita.newmobilebankig.domen.repositories.cards.impl

import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import uz.gita.newmobilebankig.data.modul.ErrorData
import uz.gita.newmobilebankig.data.modul.request.card.AddCardRequest
import uz.gita.newmobilebankig.data.modul.request.card.CardDeleteRequest
import uz.gita.newmobilebankig.data.modul.request.card.CardVerifyRequest
import uz.gita.newmobilebankig.data.modul.responses.BasicResponse
import uz.gita.newmobilebankig.data.modul.responses.card.CardVerifyData
import uz.gita.newmobilebankig.data.modul.responses.card.DataItem
import uz.gita.newmobilebankig.data.network.apies.CardApi
import uz.gita.newmobilebankig.domen.repositories.cards.CardRepository
import javax.inject.Inject

class CardRepositoryImpl @Inject constructor(private val cardApi: CardApi) : CardRepository {
    override fun addCard(addCardRequest: AddCardRequest): Flow<Result<Unit>> = flow{
        emit(getResult(cardApi.addCard(addCardRequest)))
    }

    override fun getAllCards(): Flow<Result<List<DataItem>>> = flow {
        val response = cardApi.getAll()
        if (response.isSuccessful) {
            emit(Result.success(response.body()!!.data.data))
        } else{
            var res = "Kartalar yo`q"
            response.errorBody()?.let {
                res = Gson().fromJson<ErrorData>(it.string(), ErrorData::class.java).message
            }
            emit(Result.failure(Throwable(res)))
        }
    }.flowOn(Dispatchers.IO)

    override fun verifyCard(request: CardVerifyRequest): Flow<Result<CardVerifyData>> = flow{
        val response = cardApi.verifyCard(request)
        if (response.isSuccessful) {
            emit(Result.success(response.body()!!.data))
        }
        else {
            var res = "dfs"
            response.errorBody()?.let {
                res = Gson().fromJson<ErrorData>(it.string(), ErrorData::class.java).message
            }
            emit(Result.failure(Throwable(res)))
        }
    }

    override fun deleteCard(request: CardDeleteRequest): Flow<Result<Unit>> = flow{
        emit(getResult(cardApi.deleteCard(request)))
    }

    private fun getResult(response: Response<BasicResponse>): Result<Unit> {
        return if (response.isSuccessful) {
            Result.success(Unit)
        } else {
            var res = "dfs"
            response.errorBody()?.let {
                res = Gson().fromJson<ErrorData>(it.string(), ErrorData::class.java).message
            }
            Result.failure(Throwable(res))
        }
    }
}