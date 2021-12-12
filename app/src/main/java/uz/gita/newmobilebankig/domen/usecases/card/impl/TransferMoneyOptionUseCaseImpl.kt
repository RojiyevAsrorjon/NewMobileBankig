package uz.gita.newmobilebankig.domen.usecases.card.impl

import kotlinx.coroutines.flow.Flow
import uz.gita.newmobilebankig.data.modul.request.card.CardDeleteRequest
import uz.gita.newmobilebankig.data.network.apies.CardApi
import uz.gita.newmobilebankig.domen.repositories.cards.CardRepository
import uz.gita.newmobilebankig.domen.usecases.card.TransferMoneyOptionUseCase
import javax.inject.Inject

class TransferMoneyOptionUseCaseImpl @Inject constructor(private val cardRepository: CardRepository) : TransferMoneyOptionUseCase {
    override fun deleteCard(cardDeleteRequest: CardDeleteRequest): Flow<Result<Unit>> = cardRepository.deleteCard(cardDeleteRequest)
}