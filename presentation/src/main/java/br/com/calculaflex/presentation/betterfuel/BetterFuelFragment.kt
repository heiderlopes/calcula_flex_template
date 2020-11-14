package br.com.calculaflex.presentation.betterfuel

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import br.com.calculaflex.presentation.R
import br.com.calculaflex.presentation.base.auth.BaseAuthFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
class BetterFuelFragment : BaseAuthFragment() {

    override val layout = R.layout.fragment_better_fuel

    private val betterFuelViewModel: BetterFuelViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        betterFuelViewModel.calculateBetterFuel(
            "Cruze",
            8.5,
            6.5,
            4.19,
            2.59
        )

        betterFuelViewModel.carSaveState.observe(viewLifecycleOwner, Observer {
            val bal = it
            Log.i("TAG", "TESTE")
        })
    }
}