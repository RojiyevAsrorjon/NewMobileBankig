package uz.gita.newmobilebankig.domen.usecases.card.impl

import kotlinx.coroutines.flow.Flow
import uz.gita.newmobilebankig.data.modul.responses.card.DataItem
import uz.gita.newmobilebankig.domen.repositories.cards.CardRepository
import uz.gita.newmobilebankig.domen.usecases.card.MainScreenUseCase
import javax.inject.Inject

class MainScreenUseCaseImpl @Inject constructor(private val cardRepository: CardRepository) : MainScreenUseCase{
    override fun getAllCards(): Flow<Result<List<DataItem>>> = cardRepository.getAllCards()
}