package uz.gita.newmobilebankig.domen.usecases.auth

import kotlinx.coroutines.flow.Flow
import uz.gita.newmobilebankig.data.modul.request.auth.AuthVerifyRequest

interface AuthSmsVerifyScreenUseCase {
    fun verify(request: AuthVerifyRequest): Flow<Result<Unit>>
    fun resend(): Flow<Result<Unit>>
}