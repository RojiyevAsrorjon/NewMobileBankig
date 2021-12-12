package uz.gita.newmobilebankig.domen.usecases.auth.impl

import uz.gita.newmobilebankig.domen.repositories.auth.AppRepository
import uz.gita.newmobilebankig.domen.usecases.auth.SplashScreenUseCase
import javax.inject.Inject

class SplashScreenUseCaseImpl @Inject constructor(private val appRepository: AppRepository) : SplashScreenUseCase {
    override fun startScreen(): Int = appRepository.startScreen()
}