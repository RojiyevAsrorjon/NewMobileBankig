package uz.gita.newmobilebankig.domen.usecases.card.impl

import kotlinx.coroutines.flow.Flow
import uz.gita.newmobilebankig.data.modul.request.transferMoney.request.TransferMoneyRequest
import uz.gita.newmobilebankig.data.modul.request.transferMoney.response.TransferMoneyData
import uz.gita.newmobilebankig.domen.repositories.transferMoney.TransferMoneyRepository
import uz.gita.newmobilebankig.domen.usecases.card.TransferMoneyUseCase
import javax.inject.Inject

class TransferMoneyUseCaseImpl @Inject constructor(private val repository: TransferMoneyRepository) : TransferMoneyUseCase {
    override fun transfer(request: TransferMoneyRequest): Flow<Result<TransferMoneyData>> = repository.sendMoney(request)
}