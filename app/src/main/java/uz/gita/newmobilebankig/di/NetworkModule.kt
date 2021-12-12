package uz.gita.newmobilebankig.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import uz.gita.newmobilebankig.data.database.LocalDatabase
import uz.gita.newmobilebankig.data.network.server.ApiServer
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun getRetrofit(pref : LocalDatabase) : Retrofit = ApiServer(pref).retrofit

}