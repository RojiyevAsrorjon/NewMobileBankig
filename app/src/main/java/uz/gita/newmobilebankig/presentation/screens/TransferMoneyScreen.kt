package uz.gita.newmobilebankig.presentation.screens

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.core.widget.addTextChangedListener
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
import uz.gita.newmobilebankig.data.modul.request.transferMoney.request.TransferMoneyRequest
import uz.gita.newmobilebankig.data.modul.responses.card.DataItem
import uz.gita.newmobilebankig.databinding.ScreenTransferMoneyBinding
import uz.gita.newmobilebankig.presentation.viewModels.card.CardTransferMoneyScreenViewModel
import uz.gita.newmobilebankig.presentation.viewModels.card.impl.CardTransferMoneyMoneyScreenViewModelImpl

@AndroidEntryPoint
class TransferMoneyScreen : Fragment(R.layout.screen_transfer_money) {
    private val binding by viewBinding(ScreenTransferMoneyBinding::bind)
    private val viewModelMoney: CardTransferMoneyScreenViewModel by viewModels<CardTransferMoneyMoneyScreenViewModelImpl>()
    private val navController by lazy(LazyThreadSafetyMode.NONE) { findNavController() }
    private lateinit var dataItem: DataItem
    private var enable = false
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = binding.scope {

        arguments?.let {
            dataItem = it.get("dataItem") as DataItem
        }

//        combine(
//            receiverPan.textChanges()
//                .map { it.length == 16 },
//            amountMoney.textChanges()
//                .map {
//                    checkMoney(amountMoney.text.toString().toInt())
//                },
//            transform = { pan, money -> pan && money }
//        ).onEach { sendButton.isEnabled = it }
//            .launchIn(lifecycleScope)

        sendButton.setOnClickListener {
            viewModelMoney.transfer(TransferMoneyRequest(amountMoney.text.toString().toInt(),receiverPan.text.toString(), dataItem.id))
        }

        receiverPan.addTextChangedListener { edit ->
            if (edit != null) {
                enable = edit.length == 13
            }
            check()
        }

        amountMoney.addTextChangedListener { edit ->
            enable = checkMoney(edit.toString().toInt())
            check()
        }

        observers()
    }
    private fun check() {
        binding.sendButton.isEnabled = enable
    }
    private fun checkMoney(amount: Int): Boolean {
        return if ((amount + amount * 0.01) <= dataItem.balance) {
            binding.amountMoney.setTextColor(Color.RED)
            true
        } else {
            binding.amountMoney.setTextColor(Color.GREEN)
            false
        }
    }
    @SuppressLint("FragmentLiveDataObserve")
    private fun observers() {
        viewModelMoney.showProgressBarLiveData.observe(viewLifecycleOwner, showProgressBarObserver)
        viewModelMoney.hideProgressBarLiveData.observe(viewLifecycleOwner, hideProgressBarObserver)
        viewModelMoney.enableSendButtonLiveData.observe(viewLifecycleOwner, enableSendButtonObserver)
        viewModelMoney.disableSendButtonLiveData.observe(viewLifecycleOwner, disableSendButtonObserver)
        viewModelMoney.errorMessageLiveData.observe(viewLifecycleOwner, errorMessageObserver)
        viewModelMoney.openTransferSuccessfulScreenLiveData.observe(this, openTransferSuccessfulScreenObserver)
    }

    private val showProgressBarObserver = Observer<Unit> {
        binding.progress.show()
    }
    private val hideProgressBarObserver = Observer<Unit> {
        binding.progress.hide()
    }

    private val disableSendButtonObserver = Observer<Unit> {
        binding.sendButton.isEnabled = false
    }
    private val enableSendButtonObserver = Observer<Unit> {
        binding.sendButton.isEnabled = true
    }

    private val errorMessageObserver = Observer<String> {
        Snackbar.make(binding.constraint, it, Snackbar.LENGTH_SHORT)
            .setActionTextColor(Color.RED)
            .show()
    }

    private val openTransferSuccessfulScreenObserver = Observer<Unit> {
        navController.navigate(R.id.cardTransferSuccessfulScreen)
    }

}