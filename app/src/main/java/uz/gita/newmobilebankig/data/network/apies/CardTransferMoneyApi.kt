package uz.gita.newmobilebankig.data.network.apies

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import uz.gita.newmobilebankig.data.modul.request.transferMoney.request.TransferMoneyRequest
import uz.gita.newmobilebankig.data.modul.request.transferMoney.response.TransferMoneyResponse

interface CardTransferMoneyApi {

    @POST("money-transfer/send-money")
    suspend fun sendMoney(@Body request : TransferMoneyRequest) : Response<TransferMoneyResponse>
}