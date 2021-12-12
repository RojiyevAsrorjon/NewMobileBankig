package uz.gita.newmobilebankig.domen.usecases.auth

import kotlinx.coroutines.flow.Flow
import uz.gita.newmobilebankig.data.modul.request.auth.RegisterRequest

interface RegisterScreenUseCase  {
    fun register(request : RegisterRequest) : Flow<Result<Unit>>
    fun saveData(phoneNumber : String, password : String)
}