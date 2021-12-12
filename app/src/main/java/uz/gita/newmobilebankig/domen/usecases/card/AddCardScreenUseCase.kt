package uz.gita.newmobilebankig.domen.usecases.card

import kotlinx.coroutines.flow.Flow
import uz.gita.newmobilebankig.data.modul.request.card.AddCardRequest

interface AddCardScreenUseCase {

    fun addCard(addCardRequest: AddCardRequest) : Flow<Result<Unit>>
}