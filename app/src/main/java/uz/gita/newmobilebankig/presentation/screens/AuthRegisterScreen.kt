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
import uz.gita.newmobilebankig.data.modul.request.auth.RegisterRequest
import uz.gita.newmobilebankig.databinding.ScreenAuthRegisterBinding
import uz.gita.newmobilebankig.presentation.viewModels.auth.AuthRegisterScreenViewModel
import uz.gita.newmobilebankig.presentation.viewModels.auth.impl.AuthRegisterScreenViewModelImpl

@AndroidEntryPoint
class AuthRegisterScreen : Fragment(R.layout.screen_auth_register) {
    private val viewModelAuth: AuthRegisterScreenViewModel by viewModels<AuthRegisterScreenViewModelImpl>()
    private val binding by viewBinding(ScreenAuthRegisterBinding::bind)
    private val navController by lazy(LazyThreadSafetyMode.NONE) { findNavController() }
    private var enable = false
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = binding.scope {

        userNameEdit.setOnFocusChangeListener { view, isSelected ->
            userNameLayout.setBackgroundResource(
                if (isSelected) {
                    R.drawable.bg_layout
                } else R.drawable.bg_layout_un
            )
        }

        lastNameEdit.setOnFocusChangeListener { view, isSelected ->
            lastNameLayout.setBackgroundResource(
                if (isSelected) {
                    R.drawable.bg_layout
                } else R.drawable.bg_layout_un
            )
        }
        passwordEdit.setOnFocusChangeListener { view, isSelected ->
            passwordLayout.setBackgroundResource(
                if (isSelected) {
                    R.drawable.bg_layout
                } else R.drawable.bg_layout_un
            )
        }

        phoneNumberEdit.setOnFocusChangeListener { view, isSelected ->
            phoneNumberLayout.setBackgroundResource(
                if (isSelected) {
                    R.drawable.bg_layout
                } else R.drawable.bg_layout_un
            )
        }

        combine(
            userNameEdit.textChanges()
                .map { it.isNotEmpty() },
            lastNameEdit.textChanges()
                .map { it.isNotEmpty() },
            passwordEdit.textChanges()
                .map { it.length >= 6 },

            phoneNumberEdit.textChanges()
                .map { it.length == 13 && it.startsWith("+998") },
            transform = { name, lastname, password, number -> name && lastname && password && number }
        )
            .onEach { continueButton.isEnabled = it }
            .launchIn(lifecycleScope)

        continueButton.setOnClickListener {
            viewModelAuth.saveData(
                RegisterRequest(
                    userNameEdit.text.toString(),
                    lastNameEdit.text.toString(),
                    passwordEdit.text.toString(),
                    phoneNumberEdit.text.toString(), 1
                )
            )
        }
        backButton.setOnClickListener { navController.navigateUp() }
        observers()
    }

    @SuppressLint("FragmentLiveDataObserve")
    private fun observers() {
        viewModelAuth.showProgressBarLiveData.observe(viewLifecycleOwner, showProgressBarObserver)
        viewModelAuth.hideProgressBarLiveData.observe(viewLifecycleOwner, hideProgressBarObserver)
        viewModelAuth.enableSaveButtonLiveData.observe(viewLifecycleOwner, enableContinueButtonObserver)
        viewModelAuth.disableSaveButtonLiveData.observe(viewLifecycleOwner, disableContinueButtonObserver)
        viewModelAuth.errorMessageLiveData.observe(viewLifecycleOwner, errorMessageObserver)
        viewModelAuth.openSmsVerifyScreenLiveData.observe(this, openSmsVerifyScreenObserver)
        viewModelAuth.backButtonLiveData.observe(this, backButtonObserver)
    }

    private val showProgressBarObserver = Observer<Unit> {
        binding.progress.show()
    }
    private val hideProgressBarObserver = Observer<Unit> {
        binding.progress.hide()
    }

    private val enableContinueButtonObserver = Observer<Unit> {
        binding.continueButton.isEnabled = true
    }
    private val disableContinueButtonObserver = Observer<Unit> {
        binding.continueButton.isEnabled = false
    }

    private val errorMessageObserver = Observer<String> {
        val snackBar = Snackbar.make(binding.containerLayoutRegister, it, Snackbar.LENGTH_SHORT)
            .setActionTextColor(Color.RED)
            .show()
    }

    private val openSmsVerifyScreenObserver = Observer<String> { phoneNumber ->
        val bundle = Bundle()
        bundle.putString("phoneNumber", phoneNumber)
        navController.navigate(R.id.authSmsVerifyScreen, bundle)
    }

    private val backButtonObserver = Observer<Unit> {
        navController.navigateUp()
    }
}