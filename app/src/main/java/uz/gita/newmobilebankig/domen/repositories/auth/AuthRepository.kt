package uz.gita.newmobilebankig.domen.repositories.auth

import kotlinx.coroutines.flow.Flow
import uz.gita.newmobilebankig.data.modul.request.auth.AuthVerifyRequest
import uz.gita.newmobilebankig.data.modul.request.auth.LoginRequest
import uz.gita.newmobilebankig.data.modul.request.auth.RegisterRequest

interface AuthRepository {
    fun register(request : RegisterRequest) : Flow<Result<Unit>>
    fun verify(request: AuthVerifyRequest) : Flow<Result<Unit>>

    fun login(request : LoginRequest) : Flow<Result<Unit>>

    fun saveData(phoneNumber : String, password : String)

    fun resend() : Flow<Result<Unit>>

    fun logOut() : Flow<Result<Unit>>
}