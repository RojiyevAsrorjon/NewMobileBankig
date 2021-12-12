package uz.gita.newmobilebankig.presentation.screens

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.newmobilebankig.utils.scope
import uz.gita.newmobilebankig.R
import uz.gita.newmobilebankig.databinding.ScreenConfirmedCardBinding
import uz.gita.newmobilebankig.presentation.viewModels.card.CardConfirmScreenViewModel
import uz.gita.newmobilebankig.presentation.viewModels.card.impl.CardConfirmScreenViewModelImpl

@AndroidEntryPoint
class CardConfirmedScreen : Fragment(R.layout.screen_confirmed_card) {
    private val viewBinding by viewBinding(ScreenConfirmedCardBinding::bind)
    private val navController by lazy(LazyThreadSafetyMode.NONE){ findNavController()}
    private val viewModel : CardConfirmScreenViewModel by viewModels<CardConfirmScreenViewModelImpl>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = viewBinding.scope{

        viewModel.backButtonLiveData.observe(viewLifecycleOwner, pressBackButtonObserver)
        confirmButton.setOnClickListener { viewModel.pressBackButton() }
    }
    private val pressBackButtonObserver = Observer<Unit>{
        navController.navigateUp()
    }

}