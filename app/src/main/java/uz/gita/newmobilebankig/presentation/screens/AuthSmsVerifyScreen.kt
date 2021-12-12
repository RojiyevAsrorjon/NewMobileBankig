package uz.gita.newmobilebankig.presentation.screens

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Button
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
import uz.gita.newmobilebankig.data.modul.request.auth.AuthVerifyRequest
import uz.gita.newmobilebankig.databinding.ScreenSmsVerifyBinding
import uz.gita.newmobilebankig.presentation.viewModels.auth.AuthSmsVerifyScreenViewModel
import uz.gita.newmobilebankig.presentation.viewModels.auth.impl.AuthSmsVerifyScreenViewModelImpl
import uz.gita.newmobilebankig.utils.arrangeTime

@AndroidEntryPoint
class AuthSmsVerifyScreen : Fragment(R.layout.screen_sms_verify) {
    private val viewModel: AuthSmsVerifyScreenViewModel by viewModels<AuthSmsVerifyScreenViewModelImpl>()
    private val binding by viewBinding(ScreenSmsVerifyBinding::bind)
    private val navController by lazy(LazyThreadSafetyMode.NONE) { findNavController() }
    private lateinit var timer: CountDownTimer
    private var number = ""
    private var leftTime = -1L
    override fun onResume() {
        super.onResume()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = binding.scope {
        arguments?.let {
            number = it.getString("phoneNumber", "")
            Log.d("TTT", "ph $number")
        }


        combine(
            smsEdit.textChanges()
                .map { it.length == 6 },
            transform = { sms -> sms }
        ).onEach { continueButton.isEnabled = it[0] }
            .launchIn(lifecycleScope)

        resendButton.setOnClickListener {
            viewModel.resend()
            timer.start()
            smsEdit.text = ""
        }

        continueButton.setOnClickListener {
            viewModel.verify(
                AuthVerifyRequest(
                    number, smsEdit.text.toString()
                )
            )
        }

        backButton.setOnClickListener { navController.navigateUp() }
        setOn()
        observers()
    }



    private fun setOn() {
        val list = ArrayList<Button>()
        list.add(binding.numOne)
        list.add(binding.numTwo)
        list.add(binding.numThree)
        list.add(binding.numFour)
        list.add(binding.numFive)
        list.add(binding.numSix)
        list.add(binding.numSeven)
        list.add(binding.numEight)
        list.add(binding.numNine)
        list.add(binding.numZero)

        for (i in list.indices) {
            list[i].setOnClickListener {
                val text = binding.smsEdit.text
                binding.smsEdit.text = "$text${list[i].text}"
            }
        }

        binding.removeButton.setOnClickListener {
            val text = binding.smsEdit.text
            if (text.isNotEmpty()) {
                val newText = text.substring(0, text.length-1)
                binding.smsEdit.text = newText
            }
        }
    }

    @SuppressLint("FragmentLiveDataObserve")
    private fun observers() {
        viewModel.showProgressBarLiveData.observe(viewLifecycleOwner, showProgressBarObserver)
        viewModel.hideProgressBarLiveData.observe(viewLifecycleOwner, hideProgressBarObserver)
        viewModel.enableContinueButtonLiveData.observe(viewLifecycleOwner, enableContinueButtonObserver)
        viewModel.disableContinueButtonLiveData.observe(viewLifecycleOwner, disableContinueButtonObserver)
        viewModel.openPinOptionScreenLiveData.observe(this, openPinOptionScreenObserver)
        viewModel.enableResendButtonLiveData.observe(viewLifecycleOwner, enableResendButtonObserver)
        viewModel.disableResendButtonLiveData.observe(viewLifecycleOwner, disableResendButtonObserver)
        viewModel.errorMessageLiveData.observe(viewLifecycleOwner, errorMessageObserver)
        viewModel.timerLiveData.observe(viewLifecycleOwner, timerObserver)
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

    private val openPinOptionScreenObserver = Observer<Unit> {
        navController.navigate(R.id.pinOptionScreen)
    }

    private val enableResendButtonObserver = Observer<Unit> {
        binding.resendButton.setImageResource(R.drawable.ic_reload_un)
        binding.resendButton.isEnabled = true
    }

    private val disableResendButtonObserver = Observer<Unit> {
        binding.resendButton.setImageResource(R.drawable.ic_reload)
        binding.resendButton.isEnabled = false
    }

    private val errorMessageObserver = Observer<String> {
        val snackBar = Snackbar.make(binding.containerAuth, it, Snackbar.LENGTH_SHORT)
            .setActionTextColor(Color.RED)
            .show()
    }

    private val timerObserver = Observer<Int> {
        timer = object : CountDownTimer(it * 1000L, 1000L) {
            override fun onTick(p0: Long) {
                leftTime = p0
                val time = (p0 / 1000).toInt()
                binding.textTimer.text = time.arrangeTime()
            }

            override fun onFinish() {
                viewModel.enableResendButton()
            }
        }.start()
    }

    override fun onPause() {
        super.onPause()
        Log.d("TTT", "Time : $leftTime")
        timer.cancel()
    }
}