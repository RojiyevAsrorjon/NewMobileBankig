package uz.gita.newmobilebankig.domen.repositories.cards

import kotlinx.coroutines.flow.Flow
import uz.gita.newmobilebankig.data.modul.request.card.AddCardRequest
import uz.gita.newmobilebankig.data.modul.request.card.CardDeleteRequest
import uz.gita.newmobilebankig.data.modul.request.card.CardVerifyRequest
import uz.gita.newmobilebankig.data.modul.responses.card.CardVerifyData
import uz.gita.newmobilebankig.data.modul.responses.card.DataItem

interface CardRepository {

    fun addCard(addCardRequest: AddCardRequest) : Flow<Result<Unit>>
    fun getAllCards() : Flow<Result<List<DataItem>>>
    fun verifyCard(request: CardVerifyRequest) : Flow<Result<CardVerifyData>>
    fun deleteCard(request : CardDeleteRequest) : Flow<Result<Unit>>
}