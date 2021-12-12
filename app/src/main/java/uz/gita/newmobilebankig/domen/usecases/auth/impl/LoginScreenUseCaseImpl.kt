package uz.gita.newmobilebankig.domen.usecases.auth.impl

import kotlinx.coroutines.flow.Flow
import uz.gita.newmobilebankig.data.modul.request.auth.LoginRequest
import uz.gita.newmobilebankig.domen.repositories.auth.AuthRepository
import uz.gita.newmobilebankig.domen.usecases.auth.LoginScreenUseCase
import javax.inject.Inject

class LoginScreenUseCaseImpl @Inject constructor(private val authRepository: AuthRepository) : LoginScreenUseCase{
    override fun login(request: LoginRequest): Flow<Result<Unit>> = authRepository.login(request)
}