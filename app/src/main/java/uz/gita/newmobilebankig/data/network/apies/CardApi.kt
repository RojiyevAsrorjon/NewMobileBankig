package uz.gita.newmobilebankig.data.network.apies

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import uz.gita.newmobilebankig.data.modul.request.card.AddCardRequest
import uz.gita.newmobilebankig.data.modul.request.card.CardDeleteRequest
import uz.gita.newmobilebankig.data.modul.request.card.CardVerifyRequest
import uz.gita.newmobilebankig.data.modul.responses.BasicResponse
import uz.gita.newmobilebankig.data.modul.responses.card.CardVerifyResponse
import uz.gita.newmobilebankig.data.modul.responses.card.GetAllCardsResponse

interface CardApi {

    @GET("card/all")
    suspend fun getAll() : Response<GetAllCardsResponse>

    @POST("card/total-sum")
    suspend fun getTotalSum()

    @POST("card/add-card")
    suspend fun addCard(@Body addCardRequest: AddCardRequest) : Response<BasicResponse>

    @POST("card/verify")
    suspend fun verifyCard(@Body verifyRequest: CardVerifyRequest) : Response<CardVerifyResponse>

    @POST("card/delete-card")
    suspend fun deleteCard(@Body request : CardDeleteRequest) : Response<BasicResponse>


    // card edit response -> BasicResponse
    // card delete response -> BasicResponse
    // card get all body token ->
}