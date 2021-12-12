package uz.gita.newmobilebankig.presentation.screens

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
import uz.gita.newmobilebankig.databinding.ScreenAuthPinOptionBinding
import uz.gita.newmobilebankig.presentation.viewModels.auth.AuthPinScreenViewModel
import uz.gita.newmobilebankig.presentation.viewModels.auth.impl.AuthPinScreenViewModelImpl

@AndroidEntryPoint
class AuthPinOptionScreen : Fragment(R.layout.screen_auth_pin_option) {
    private val viewModelAuth: AuthPinScreenViewModel by viewModels<AuthPinScreenViewModelImpl>()
    private val binding by viewBinding(ScreenAuthPinOptionBinding::bind)
    private var isSwitched = false
    private val navController by lazy(LazyThreadSafetyMode.NONE) { findNavController() }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = binding.scope{
        toggleButton.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (isChecked) {
                isSwitched = true
                viewModelAuth.showEditTexts()
            } else {
                isSwitched = false
                viewModelAuth.hideEditTexts()
            }
        }

        combine(
            pinEdit.textChanges()
                .map { it.length == 5 },
            pinConfirm.textChanges()
                .map { it.length == 5 },
            transform = {pin, conPin -> pin == conPin}
        ).onEach { continueButton.isEnabled = it }
            .launchIn(lifecycleScope)

        continueButton.setOnClickListener {
            if (isSwitched) {
                if (pinEdit.text.toString() == pinConfirm.text.toString()) {
                    viewModelAuth.setPinScreen(pinEdit.text.toString())
                }
                else{
                    Snackbar.make(layoutPin, "Pin tasdiqlanmadi", Snackbar.LENGTH_SHORT)
                        .show()
                }
            } else viewModelAuth.setMainScreen()
        }

        observers()
    }

    private fun observers() {
        viewModelAuth.disableContinueButtonLiveData.observe(viewLifecycleOwner, disableContinueButtonObserver)
        viewModelAuth.enableContinueButtonLiveData.observe(viewLifecycleOwner, enableContinueButtonObserver)
        viewModelAuth.disableEditTextLiveData.observe(viewLifecycleOwner, disableEditTextObserver)
        viewModelAuth.enableEditTextLiveData.observe(viewLifecycleOwner, enableEditTextObserver)
        viewModelAuth.openMainScreenLiveData.observe(viewLifecycleOwner, openMainScreenObserver)
    }

    private val enableContinueButtonObserver = Observer<Unit>{
        binding.continueButton.isEnabled = true
    }

    private val disableContinueButtonObserver = Observer<Unit>{
        binding.continueButton.isEnabled = false
    }

    private val enableEditTextObserver = Observer<Unit>{
        binding.pinEdit.visibility = View.VISIBLE
        binding.pinConfirm.visibility = View.VISIBLE
    }

    private val disableEditTextObserver = Observer<Unit>{
        binding.pinEdit.visibility = View.GONE
        binding.pinConfirm.visibility = View.GONE
    }

    private val openMainScreenObserver = Observer<Unit>{
        navController.navigate(R.id.mainScreen)
    }
}