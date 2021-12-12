package uz.gita.newmobilebankig.domen.usecases.auth.impl

import kotlinx.coroutines.flow.Flow
import uz.gita.newmobilebankig.data.modul.request.auth.RegisterRequest
import uz.gita.newmobilebankig.domen.repositories.auth.AuthRepository
import uz.gita.newmobilebankig.domen.usecases.auth.RegisterScreenUseCase
import javax.inject.Inject

class RegisterScreenUseCaseImpl @Inject constructor(private val authRepository: AuthRepository) : RegisterScreenUseCase {
    override fun register(request: RegisterRequest): Flow<Result<Unit>> = authRepository.register(request)

    override fun saveData(phoneNumber: String, password: String) = authRepository.saveData(phoneNumber, password)
}