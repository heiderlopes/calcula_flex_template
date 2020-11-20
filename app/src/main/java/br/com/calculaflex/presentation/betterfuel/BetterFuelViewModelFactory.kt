package br.com.calculaflex.presentation.betterfuel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.calculaflex.domain.usecases.*

class BetterFuelViewModelFactory(
    private val saveCarUseCase: SaveCarUseCase,
    private val calculateBetterFuelUseCase: CalculateBetterFuelUseCase,
    private val getCarUseCase: GetCarUseCase,
    private val getUserLoggedUseCase: GetUserLoggedUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(
            SaveCarUseCase::class.java,
            CalculateBetterFuelUseCase::class.java,
            GetCarUseCase::class.java,
            GetUserLoggedUseCase::class.java
        )
            .newInstance(saveCarUseCase, calculateBetterFuelUseCase, getCarUseCase,
                getUserLoggedUseCase)
    }
}