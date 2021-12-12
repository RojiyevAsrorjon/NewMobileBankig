package uz.gita.newmobilebankig.domen.repositories.auth.impl

import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import uz.gita.newmobilebankig.data.database.LocalDatabase
import uz.gita.newmobilebankig.data.modul.ErrorData
import uz.gita.newmobilebankig.data.modul.request.auth.AuthVerifyRequest
import uz.gita.newmobilebankig.data.modul.request.auth.LoginRequest
import uz.gita.newmobilebankig.data.modul.request.auth.RegisterRequest
import uz.gita.newmobilebankig.data.modul.request.auth.ResendRequest
import uz.gita.newmobilebankig.data.modul.responses.BasicResponse
import uz.gita.newmobilebankig.data.network.apies.AuthApi
import uz.gita.newmobilebankig.domen.repositories.auth.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val authApi: AuthApi, private val pref : LocalDatabase) : AuthRepository {

    override fun register(request: RegisterRequest): Flow<Result<Unit>> = flow {
        emit(getResult(authApi.register(request)))
    }

    override fun verify(request: AuthVerifyRequest): Flow<Result<Unit>> = flow {
        try {
            val response = authApi.verify(request)
            if (response.code() == 200) {
                response.body()?.let {
                    pref.accessToken = it.data.access_token
                    pref.refreshToken = it.data.refresh_token
                }
                emit(Result.success(Unit))
            } else {
                var res = "Sms kod xato kiritildi"
                response.errorBody()?.let {
                    res = Gson().fromJson<ErrorData>(it.string(), ErrorData::class.java).message
                }
                emit(Result.failure(Throwable(res)))
            }
        } catch (e: Exception) {
            emit(Result.failure<Unit>(Throwable("Sms kod xato kiritildi")))
        }
    }.flowOn(Dispatchers.IO)

    override fun login(request: LoginRequest): Flow<Result<Unit>> = flow{
        emit(getResult(authApi.login(request)))
    }

    override fun saveData(phoneNumber: String, password: String) {
        pref.phoneNumber = phoneNumber
        pref.pin = password
    }

    override fun resend(): Flow<Result<Unit>> = flow{
        emit(getResult(authApi.resend(ResendRequest(pref.phoneNumber, pref.pin))))
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

    override fun logOut(): Flow<Result<Unit>> = flow{
        emit(getResult(authApi.logOut()))
    }
}