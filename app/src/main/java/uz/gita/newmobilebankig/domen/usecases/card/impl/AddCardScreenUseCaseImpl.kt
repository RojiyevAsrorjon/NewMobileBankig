package uz.gita.newmobilebankig.domen.usecases.card.impl

import kotlinx.coroutines.flow.Flow
import uz.gita.newmobilebankig.data.modul.request.card.AddCardRequest
import uz.gita.newmobilebankig.domen.repositories.cards.CardRepository
import uz.gita.newmobilebankig.domen.usecases.card.AddCardScreenUseCase
import javax.inject.Inject

class AddCardScreenUseCaseImpl @Inject constructor(private val cardRepository: CardRepository) : AddCardScreenUseCase {
    override fun addCard(addCardRequest: AddCardRequest): Flow<Result<Unit>> = cardRepository.addCard(addCardRequest)
}