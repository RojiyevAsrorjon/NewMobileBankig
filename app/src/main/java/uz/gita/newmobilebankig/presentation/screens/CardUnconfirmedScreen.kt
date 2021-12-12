package uz.gita.newmobilebankig.presentation.screens

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.newmobilebankig.utils.scope
import uz.gita.newmobilebankig.R
import uz.gita.newmobilebankig.databinding.ScreenUnconfirmedCardBinding

@AndroidEntryPoint
class CardUnconfirmedScreen : Fragment(R.layout.screen_unconfirmed_card) {
    private val binding  by viewBinding(ScreenUnconfirmedCardBinding::bind)
    private val navController by lazy(LazyThreadSafetyMode.NONE){ findNavController()}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)  = binding.scope{
        confirmButton.setOnClickListener { navController.navigateUp() }
    }
}