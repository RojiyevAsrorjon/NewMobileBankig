package uz.gita.newmobilebankig.presentation.screens

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import ru.ldralighieri.corbind.widget.textChanges
import uz.gita.newmobilebankig.utils.scope
import uz.gita.newmobilebankig.R
import uz.gita.newmobilebankig.data.modul.request.card.AddCardRequest
import uz.gita.newmobilebankig.databinding.ScreenAddCardBinding
import uz.gita.newmobilebankig.presentation.viewModels.card.CardAddScreenViewModel
import uz.gita.newmobilebankig.presentation.viewModels.card.impl.CardAddScreenViewModelImpl

@AndroidEntryPoint
class CardAddScreen : Fragment(R.layout.screen_add_card) {
    private val viewModelAdd : CardAddScreenViewModel by viewModels<CardAddScreenViewModelImpl>()
    private val binding by viewBinding(ScreenAddCardBinding::bind)
    private val navController by lazy(LazyThreadSafetyMode.NONE) { findNavController() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = binding.scope {
        combine(
            cardNumber.textChanges()
                .map { it.length == 16 },
            expireDate.textChanges()
                .map { it.length == 5 },
            cardName.textChanges()
                .map { it.isNotEmpty() },
            transform = { number, pan, name -> number && pan && name }
        )
            .onEach { addCard.isEnabled = it }
            .launchIn(lifecycleScope)


        addCard.setOnClickListener {
            viewModelAdd.addCard(
                AddCardRequest(
                    cardName.text.toString(), cardNumber.text.toString(), expireDate.text.toString()
                )
            )
        }


        observers()
    }

    @SuppressLint("FragmentLiveDataObserve")
    private fun observers() {
        viewModelAdd.showProgressBarLiveData.observe(viewLifecycleOwner, showProgressBarObserver)
        viewModelAdd.hideProgressBarLiveData.observe(viewLifecycleOwner, hideProgressBarObserver)
        viewModelAdd.enableSaveButtonLiveData.observe(viewLifecycleOwner, enableContinueButtonObserver)
        viewModelAdd.disableSaveButtonLiveData.observe(viewLifecycleOwner, disableContinueButtonObserver)
        viewModelAdd.errorMessageLiveData.observe(viewLifecycleOwner, errorMessageObserver)
        viewModelAdd.openCardSmsVerifyScreenLiveData.observe(this, openSmsVerifyScreenObserver)
    }

    private val showProgressBarObserver = Observer<Unit> {
        binding.progress.show()
    }
    private val hideProgressBarObserver = Observer<Unit> {
        binding.progress.hide()
    }

    private val enableContinueButtonObserver = Observer<Unit> {
        binding.addCard.isEnabled = true
    }
    private val disableContinueButtonObserver = Observer<Unit> {
        binding.addCard.isEnabled = false
    }

    private val errorMessageObserver = Observer<String> {
        val snackBar = Snackbar.make(binding.layoutAddCard, it, Snackbar.LENGTH_SHORT)
            .setActionTextColor(Color.RED)
            .show()
    }

    private val openSmsVerifyScreenObserver = Observer<String> { pan ->
        val bundle = Bundle()
        bundle.putString("pan", pan)
        navController.navigate(R.id.cardSmsVerifyScreen, bundle)
    }
}