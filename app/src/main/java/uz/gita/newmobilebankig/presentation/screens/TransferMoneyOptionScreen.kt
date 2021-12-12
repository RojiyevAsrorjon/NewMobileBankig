package uz.gita.newmobilebankig.presentation.screens

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.newmobilebankig.utils.scope
import uz.gita.newmobilebankig.R
import uz.gita.newmobilebankig.data.modul.request.card.CardDeleteRequest
import uz.gita.newmobilebankig.data.modul.responses.card.DataItem
import uz.gita.newmobilebankig.databinding.ScreenTransferMoneyOptionBinding
import uz.gita.newmobilebankig.presentation.viewModels.card.CardTransferOptionScreenViewModel
import uz.gita.newmobilebankig.presentation.viewModels.card.impl.CardTransferOptionScreenViewModelImpl

@AndroidEntryPoint
class TransferMoneyOptionScreen : Fragment(R.layout.screen_transfer_money_option) {
    private val binding by viewBinding(ScreenTransferMoneyOptionBinding::bind)
    private val viewModel: CardTransferOptionScreenViewModel by viewModels<CardTransferOptionScreenViewModelImpl>()
    private val navController by lazy(LazyThreadSafetyMode.NONE) { findNavController() }
    private lateinit var dataItem: DataItem
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = binding.scope{
        arguments?.let {
            dataItem = it.get("dataItem") as DataItem
            cardName.text = dataItem.cardName
            cardNumber.text = dataItem.pan
            expireDate.text = dataItem.exp
        }

        sendMoneyButton.setOnClickListener { viewModel.openTransferScreen(dataItem) }
        feeCardButton.setOnClickListener { viewModel.openFee() }
        deleteCardButton.setOnClickListener {
            val dialog = AlertDialog.Builder(requireContext())
            dialog.setPositiveButton("Yes",DialogInterface.OnClickListener { _, i ->
                viewModel.deleteCard(CardDeleteRequest(dataItem.pan))
            })
            dialog.setNegativeButton("No") { d, i -> }
            dialog.show()
        }

        observers()
    }

    @SuppressLint("FragmentLiveDataObserve")
    private fun observers() {
        viewModel.showProgressBarLiveData.observe(viewLifecycleOwner, showProgressBarObserver)
        viewModel.hideProgressBarLiveData.observe(viewLifecycleOwner, hideProgressBarObserver)
        viewModel.disableButtonsLiveData.observe(viewLifecycleOwner, disableButtonsObserver)
        viewModel.enableButtonsLiveData.observe(viewLifecycleOwner, enableButtonsObserver)
        viewModel.errorMessageLiveData.observe(viewLifecycleOwner, errorMessageObserver)
        viewModel.openTransferScreenLiveData.observe(this, openTransferScreenObserver)
    }

    private val showProgressBarObserver = Observer<Unit> {
        binding.progress.show()
    }
    private val hideProgressBarObserver = Observer<Unit> {
        binding.progress.hide()
    }

    private val enableButtonsObserver = Observer<Unit>{
        binding.sendMoneyButton.isEnabled = true
        binding.feeCardButton.isEnabled = true
        binding.deleteCardButton.isEnabled = true
    }

    private val disableButtonsObserver = Observer<Unit>{
        binding.sendMoneyButton.isEnabled = false
        binding.feeCardButton.isEnabled = false
        binding.deleteCardButton.isEnabled = false
    }

    private val errorMessageObserver = Observer<String> {
        Snackbar.make(binding.constraint, it, Snackbar.LENGTH_SHORT)
            .setActionTextColor(Color.RED)
            .show()
    }

    private val openTransferScreenObserver = Observer<DataItem>{
        val bundle = Bundle()
        bundle.putSerializable("dataItem", it)
        navController.navigate(R.id.transferMoneyScreen, bundle)
    }

}