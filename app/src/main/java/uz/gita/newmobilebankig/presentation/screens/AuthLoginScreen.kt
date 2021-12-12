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
import uz.gita.newmobilebankig.data.modul.request.auth.LoginRequest
import uz.gita.newmobilebankig.databinding.ScreenAuthLoginBinding
import uz.gita.newmobilebankig.presentation.viewModels.auth.AuthLoginScreenViewModel
import uz.gita.newmobilebankig.presentation.viewModels.auth.impl.LoginScreenViewModelImpl

@AndroidEntryPoint
class AuthLoginScreen : Fragment(R.layout.screen_auth_login) {
    private val binding by viewBinding(ScreenAuthLoginBinding::bind)
    private val viewModelAuth: AuthLoginScreenViewModel by viewModels<LoginScreenViewModelImpl>()
    private val navController by lazy(LazyThreadSafetyMode.NONE) { findNavController() }
    private var enable = false
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = binding.scope {
        phoneNumberEdit.setOnFocusChangeListener { view, isSelected ->
            phoneNumberLayout.setBackgroundResource(
                if (isSelected) {
                    R.drawable.bg_layout
                } else R.drawable.bg_layout_un
            )
        }

        passwordEdit.setOnFocusChangeListener { view, isSelected ->
            passwordLayout.setBackgroundResource(
                if (isSelected) R.drawable.bg_layout else R.drawable.bg_layout_un
            )
        }

//        phoneNumberEdit.addTextChangedListener {
//            it?.let {
//                enable = it.length == 13 && it.startsWith("+998")
//                check()
//            }
//        }
//
//        passwordEdit.addTextChangedListener {
//            it?.let {
//                enable = it.length in 6..14
//                check()
//            }
//        }

        combine(
            phoneNumberEdit.textChanges()
                .map { it.length == 13 && it.startsWith("+998") },

            passwordEdit.textChanges()
                .map { it.length>=6 },

            transform = { phone, password -> phone && password }
        ).onEach { continueButton.isEnabled = it }
            .launchIn(lifecycleScope)

        continueButton.setOnClickListener { viewModelAuth.login(LoginRequest(phoneNumberEdit.text.toString(), passwordEdit.text.toString())) }

        registerButton.setOnClickListener { viewModelAuth.openRegisterScreen() }


        observers()
    }



    @SuppressLint("FragmentLiveDataObserve")
    fun observers() {
        viewModelAuth.showProgressBarLiveData.observe(viewLifecycleOwner, showProgressBarObserver)
        viewModelAuth.hideProgressBarLiveData.observe(viewLifecycleOwner, hideProgressBarObserver)
        viewModelAuth.enableContinueButtonLiveData.observe(viewLifecycleOwner, enableContinueButtonObserver)
        viewModelAuth.disableContinueButtonLiveData.observe(viewLifecycleOwner, disableContinueButtonObserver)
        viewModelAuth.errorMessageLiveData.observe(viewLifecycleOwner, errorMessageObserver)
        viewModelAuth.openRegisterScreenLiveData.observe(this, openRegisterScreenObserver)
        viewModelAuth.openSmsVerifyScreenLiveData.observe(this, openSmsVerifyScreenObserver)
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
        val snackBar = Snackbar.make(binding.containerLayoutLogin, it, Snackbar.LENGTH_SHORT)
            .setActionTextColor(Color.RED)
            .show()
    }

    private val openSmsVerifyScreenObserver = Observer<String> { phoneNumber ->
        val bundle = Bundle()
        bundle.putString("phoneNumber", phoneNumber)
        navController.navigate(R.id.authSmsVerifyScreen, bundle)
    }

    private val openRegisterScreenObserver = Observer<Unit> {
        navController.navigate(R.id.registerScreen)
    }


}