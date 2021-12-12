package uz.gita.newmobilebankig.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uz.gita.newmobilebankig.data.database.LocalDatabase
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    @Singleton
    fun getSharedPreferences(@ApplicationContext context : Context) : LocalDatabase = LocalDatabase(context)
}