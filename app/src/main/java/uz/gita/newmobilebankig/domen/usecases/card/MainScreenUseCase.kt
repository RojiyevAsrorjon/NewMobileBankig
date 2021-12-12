package uz.gita.newmobilebankig.domen.usecases.card

import kotlinx.coroutines.flow.Flow
import uz.gita.newmobilebankig.data.modul.responses.card.DataItem

interface MainScreenUseCase {

    fun getAllCards(): Flow<Result<List<DataItem>>>
}