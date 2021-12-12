package uz.gita.newmobilebankig.presentation.screens

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.newmobilebankig.utils.scope
import uz.gita.newmobilebankig.R
import uz.gita.newmobilebankig.data.modul.responses.card.DataItem
import uz.gita.newmobilebankig.databinding.ScreenCardMainBinding
import uz.gita.newmobilebankig.presentation.adapters.RecyclerCardAdapter
import uz.gita.newmobilebankig.presentation.viewModels.card.CardMainScreenViewModel
import uz.gita.newmobilebankig.presentation.viewModels.card.impl.CardMainScreenViewModelImpl

@AndroidEntryPoint
class CardMainScreen : Fragment(R.layout.screen_card_main) {
    private val viewModelCard: CardMainScreenViewModel by viewModels<CardMainScreenViewModelImpl>()
    private val binding by viewBinding(ScreenCardMainBinding::bind)
    private val adapter= RecyclerCardAdapter()
    private val navController by lazy(LazyThreadSafetyMode.NONE) { findNavController() }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = binding.scope {

        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(requireContext())

        addButton.setOnClickListener {
            viewModelCard.openMainScreen()
        }
        adapter.setListener { dataItem ->
            viewModelCard.openTransferOptionScreen(dataItem)
        }

        observers()
    }
    @SuppressLint("FragmentLiveDataObserve")
    private fun observers() {
        viewModelCard.cardsListLiveData.observe(viewLifecycleOwner, cardsListObserver)
        viewModelCard.errorMessageLiveData.observe(viewLifecycleOwner, errorMessageObserver)
        viewModelCard.openAddCardLiveData.observe(this, openAddScreenObserver)
        viewModelCard.showProgressBarLiveData.observe(viewLifecycleOwner, showProgressBarObserver)
        viewModelCard.hideProgressBarLiveData.observe(viewLifecycleOwner, hideProgressBarObserver)
        viewModelCard.openTransferOptionScreenLiveData.observe(this, openTransferOptionScreenObserver)
    }

    private val cardsListObserver = Observer<List<DataItem>>{
        adapter.submitList(it)
        haveOrNotState(it)
    }

    private val errorMessageObserver = Observer<String>{
        Snackbar.make(binding.layoutMain, it, Snackbar.LENGTH_SHORT)
            .setActionTextColor(Color.RED)
            .show()
    }

    private val openAddScreenObserver = Observer<Unit>{
        navController.navigate(R.id.addCardScreen)
    }

    private val showProgressBarObserver = Observer<Unit> {
        binding.progress.show()
    }
    private val hideProgressBarObserver = Observer<Unit> {
        binding.progress.hide()
    }

    private val openTransferOptionScreenObserver = Observer<DataItem>{ dataItem ->
        val bundle = Bundle()
        bundle.putSerializable("dataItem", dataItem)
        navController.navigate(R.id.transferMoneyOptionScreen, bundle)
    }

    private fun haveOrNotState(list: List<DataItem>) {
        binding.notYetText.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
    }
}