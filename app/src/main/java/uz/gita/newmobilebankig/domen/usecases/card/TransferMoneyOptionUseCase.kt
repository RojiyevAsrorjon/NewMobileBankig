package uz.gita.newmobilebankig.domen.usecases.card

import kotlinx.coroutines.flow.Flow
import uz.gita.newmobilebankig.data.modul.request.card.CardDeleteRequest

interface TransferMoneyOptionUseCase {
    fun deleteCard(cardDeleteRequest: CardDeleteRequest) : Flow<Result<Unit>>
}