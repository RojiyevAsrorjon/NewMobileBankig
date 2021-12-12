package uz.gita.newmobilebankig.presentation.screens

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.newmobilebankig.R
import uz.gita.newmobilebankig.presentation.viewModels.auth.SplashScreenViewModel
import uz.gita.newmobilebankig.presentation.viewModels.auth.impl.SplashScreenViewModelImpl

@AndroidEntryPoint
class SplashScreen : Fragment(R.layout.screen_splash) {
    private val viewModel : SplashScreenViewModel by viewModels<SplashScreenViewModelImpl>()
    private val navController by lazy(LazyThreadSafetyMode.NONE) { findNavController() }
    @SuppressLint("FragmentLiveDataObserve")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        viewModel.openIdenScreenLiveData.observe(this, openIdenScreenObserver)
        viewModel.openLoginScreenLiveData.observe(this, openLoginScreenObserver)
        viewModel.openMainScreenLiveData.observe(this, openMainScreenObserver)
    }

    private val openLoginScreenObserver = Observer<Unit>{
        navController.navigate(R.id.loginScreen)
    }
    private val openMainScreenObserver = Observer<Unit>{
        navController.navigate(R.id.mainScreen)
    }
    private val openIdenScreenObserver = Observer<Unit>{
        navController.navigate(R.id.idenScreen)
    }
}