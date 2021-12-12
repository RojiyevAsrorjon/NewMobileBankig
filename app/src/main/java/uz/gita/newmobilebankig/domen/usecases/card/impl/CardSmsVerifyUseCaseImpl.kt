package uz.gita.newmobilebankig.domen.usecases.card.impl

import kotlinx.coroutines.flow.Flow
import uz.gita.newmobilebankig.data.modul.request.card.CardVerifyRequest
import uz.gita.newmobilebankig.data.modul.responses.card.CardVerifyData
import uz.gita.newmobilebankig.domen.repositories.cards.CardRepository
import uz.gita.newmobilebankig.domen.usecases.card.CardSmsVerifyUseCase
import javax.inject.Inject


class CardSmsVerifyUseCaseImpl @Inject constructor(private val cardRepository: CardRepository) : CardSmsVerifyUseCase{
    override fun verify(request: CardVerifyRequest): Flow<Result<CardVerifyData>> = cardRepository.verifyCard(request)
}