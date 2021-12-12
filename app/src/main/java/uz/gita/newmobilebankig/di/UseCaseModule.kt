package uz.gita.newmobilebankig.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.newmobilebankig.domen.usecases.auth.*
import uz.gita.newmobilebankig.domen.usecases.auth.impl.*
import uz.gita.newmobilebankig.domen.usecases.card.*
import uz.gita.newmobilebankig.domen.usecases.card.impl.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface UseCaseModule {


    @Binds
    @Singleton
    fun getSplashScreenUseCase(splashScreenUseCaseImpl: SplashScreenUseCaseImpl) : SplashScreenUseCase


    @Binds
    @Singleton
    fun getRegisterScreenUseCase(registerScreenUseCaseImpl: RegisterScreenUseCaseImpl) : RegisterScreenUseCase

    @Binds
    @Singleton
    fun getLoginScreenUseCase(loginScreenUseCaseImpl: LoginScreenUseCaseImpl) : LoginScreenUseCase

    @Binds
    @Singleton
    fun getAuthSmsVerifyScreenUseCase(authSmsScreenVerifyUseCaseImpl: AuthSmsScreenVerifyUseCaseImpl): AuthSmsVerifyScreenUseCase

    @Binds
    @Singleton
    fun getPinScreenUseCase(pinScreenUseCaseImpl: PinScreenUseCaseImpl) : PinScreenUseCase

    @Binds
    @Singleton
    fun getMainScreenUseCase(mainScreenUseCaseImpl: MainScreenUseCaseImpl) : MainScreenUseCase

    @Binds
    @Singleton
    fun getAddCardScreenUseCase(addScreenUseCaseImpl: AddCardScreenUseCaseImpl) : AddCardScreenUseCase

    @Binds
    @Singleton
    fun getCardSmsVerifyScreenUseCase(cardSmsVerifyUseCase: CardSmsVerifyUseCaseImpl) : CardSmsVerifyUseCase

    @Binds
    @Singleton
    fun getTransferMoneyOptionUseCase(transferMoneyOptionUseCaseImpl: TransferMoneyOptionUseCaseImpl): TransferMoneyOptionUseCase

    @Binds
    @Singleton
    fun getTransferMoneyUseCase(transferMoneyUseCaseImpl: TransferMoneyUseCaseImpl) : TransferMoneyUseCase

}