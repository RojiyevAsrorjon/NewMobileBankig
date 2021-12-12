package uz.gita.newmobilebankig.domen.usecases.auth

import kotlinx.coroutines.flow.Flow
import uz.gita.newmobilebankig.data.modul.request.auth.LoginRequest

interface LoginScreenUseCase {
    fun login(request : LoginRequest) : Flow<Result<Unit>>
}