package uz.gita.newmobilebankig.presentation.screens

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.Toast
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
import uz.gita.newmobilebankig.data.modul.request.card.CardVerifyRequest
import uz.gita.newmobilebankig.databinding.ScreenCardSmsVerifyBinding
import uz.gita.newmobilebankig.presentation.viewModels.card.CardSmsVerifyViewModel
import uz.gita.newmobilebankig.presentation.viewModels.card.impl.CardSmsVerifyScreenViewModelImpl
import uz.gita.newmobilebankig.utils.arrangeTime

@AndroidEntryPoint
class CardSmsVerifyScreen : Fragment(R.layout.screen_card_sms_verify) {
    private val navController by lazy(LazyThreadSafetyMode.NONE) { findNavController() }
    private val binding by viewBinding(ScreenCardSmsVerifyBinding::bind)
    private val viewModel: CardSmsVerifyViewModel by viewModels<CardSmsVerifyScreenViewModelImpl>()
    private var pan = ""
    private lateinit var timer : CountDownTimer
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = binding.scope {

        combine(
            smsEdit.textChanges()
                .map { it.length == 6 },
            transform = { sms -> sms }
        ).onEach { continueButton.isEnabled = it[0] }
            .launchIn(lifecycleScope)

        arguments?.let {
            pan = it.getString("pan", "")
        }


        continueButton.setOnClickListener {
            Toast.makeText(requireContext(), smsEdit.text.toString(), Toast.LENGTH_SHORT).show()
            viewModel.verifyCard(
                CardVerifyRequest(
                    pan, smsEdit.text.toString()
                )
            )
        }


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
        viewModel.errorMessageLiveData.observe(viewLifecycleOwner, errorMessageObserver)
        viewModel.openConfirmedCardScreenLiveData.observe(this, openConfirmedScreenObserver)
        viewModel.openUnconfirmedScreenLiveData.observe(this, openUnconfirmedScreenObserver)
        viewModel.timerObserver.observe(viewLifecycleOwner, timerObserver)
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
        Snackbar.make(binding.container, it, Snackbar.LENGTH_SHORT)
            .setActionTextColor(Color.RED)
            .show()
    }

    private val openConfirmedScreenObserver = Observer<Unit> {
        navController.navigate(R.id.confirmedCardScreen)
    }
    private val openUnconfirmedScreenObserver = Observer<Unit> {
        navController.navigate(R.id.cardUnconfirmedScreen)
    }
    private val timerObserver = Observer<Int>{
        timer = object : CountDownTimer(it * 1000L, 1000L) {
            override fun onTick(p0: Long) {
                val time = (p0 / 1000).toInt()
                binding.textTimer.text = time.arrangeTime()
            }

            override fun onFinish() {
                viewModel.openUnconfirmedScreen()
            }
        }.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        timer.cancel()
    }

}