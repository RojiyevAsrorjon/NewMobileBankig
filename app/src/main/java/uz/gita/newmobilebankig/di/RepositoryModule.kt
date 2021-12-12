package uz.gita.newmobilebankig.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.newmobilebankig.domen.repositories.auth.AppRepository
import uz.gita.newmobilebankig.domen.repositories.auth.AuthRepository
import uz.gita.newmobilebankig.domen.repositories.auth.impl.AppRepositoryImpl
import uz.gita.newmobilebankig.domen.repositories.auth.impl.AuthRepositoryImpl
import uz.gita.newmobilebankig.domen.repositories.cards.CardRepository
import uz.gita.newmobilebankig.domen.repositories.cards.impl.CardRepositoryImpl
import uz.gita.newmobilebankig.domen.repositories.transferMoney.TransferMoneyRepository
import uz.gita.newmobilebankig.domen.repositories.transferMoney.impl.TransferMoneyRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun getAppRepository(appRepositoryImpl: AppRepositoryImpl) : AppRepository

    @Binds
    @Singleton
    fun getAuthRepository(authRepositoryImpl: AuthRepositoryImpl) : AuthRepository

    @Binds
    @Singleton
    fun getCardRepository(cardRepositoryImpl: CardRepositoryImpl) : CardRepository

    @Binds
    @Singleton
    fun getTransferMoneyRepository(transferMoneyRepositoryImpl: TransferMoneyRepositoryImpl) : TransferMoneyRepository
}