package uz.gita.newmobilebankig.domen.usecases.card

import kotlinx.coroutines.flow.Flow
import uz.gita.newmobilebankig.data.modul.request.card.CardVerifyRequest
import uz.gita.newmobilebankig.data.modul.responses.card.CardVerifyData

interface CardSmsVerifyUseCase {
    fun verify(request : CardVerifyRequest) : Flow<Result<CardVerifyData>>
}