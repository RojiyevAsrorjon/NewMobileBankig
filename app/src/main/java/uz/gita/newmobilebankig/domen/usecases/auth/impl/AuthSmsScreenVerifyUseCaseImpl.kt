package uz.gita.newmobilebankig.domen.usecases.auth.impl

import kotlinx.coroutines.flow.Flow
import uz.gita.newmobilebankig.data.modul.request.auth.AuthVerifyRequest
import uz.gita.newmobilebankig.domen.repositories.auth.AppRepository
import uz.gita.newmobilebankig.domen.repositories.auth.AuthRepository
import uz.gita.newmobilebankig.domen.usecases.auth.AuthSmsVerifyScreenUseCase
import javax.inject.Inject

class AuthSmsScreenVerifyUseCaseImpl @Inject constructor(private val authRepository: AuthRepository, private val appRepository: AppRepository) : AuthSmsVerifyScreenUseCase {
    override fun verify(request: AuthVerifyRequest): Flow<Result<Unit>> = authRepository.verify(request)
    override fun resend(): Flow<Result<Unit>> = authRepository.resend()

}