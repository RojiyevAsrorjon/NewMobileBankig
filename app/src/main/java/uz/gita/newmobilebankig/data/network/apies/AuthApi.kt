package uz.gita.newmobilebankig.data.network.apies

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import uz.gita.newmobilebankig.data.modul.request.auth.AuthVerifyRequest
import uz.gita.newmobilebankig.data.modul.request.auth.LoginRequest
import uz.gita.newmobilebankig.data.modul.request.auth.RegisterRequest
import uz.gita.newmobilebankig.data.modul.request.auth.ResendRequest
import uz.gita.newmobilebankig.data.modul.responses.BasicResponse
import uz.gita.newmobilebankig.data.modul.responses.auth.VerifyResponse

interface AuthApi {

    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<BasicResponse>

    @POST("auth/verify")
    suspend fun verify(@Body request: AuthVerifyRequest): Response<VerifyResponse>

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<BasicResponse>

    @POST("auth/resend")
    suspend fun resend(@Body request: ResendRequest): Response<BasicResponse>

    @POST("auth/logout")
    suspend fun logOut() : Response<BasicResponse>
}