package uz.gita.newmobilebankig.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import uz.gita.newmobilebankig.data.network.apies.AuthApi
import uz.gita.newmobilebankig.data.network.apies.CardApi
import uz.gita.newmobilebankig.data.network.apies.CardTransferMoneyApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Provides
    @Singleton
    fun getAuthApi(retrofit : Retrofit) : AuthApi = retrofit.create(AuthApi::class.java)

    @Provides
    @Singleton
    fun getCardApi(retrofit : Retrofit) : CardApi = retrofit.create(CardApi::class.java)

    @Provides
    @Singleton
    fun getTransferMoneyApi(retrofit: Retrofit) : CardTransferMoneyApi = retrofit.create(CardTransferMoneyApi::class.java)
}