package br.com.calculaflex.presentation.betterfuel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.calculaflex.domain.entity.Car
import br.com.calculaflex.domain.entity.RequestState
import br.com.calculaflex.domain.usecases.GetUserLoggedUseCase
import br.com.calculaflex.domain.usecases.SaveCarUseCase
import kotlinx.coroutines.launch

class BetterFuelViewModel(
    private val saveCarUseCase: SaveCarUseCase
) : ViewModel() {

    var carSaveState = MutableLiveData<RequestState<Car>>()

    fun saveCar(
        vehicle: String,
        kmGasolinePerLiter: Double,
        kmEthanolPerLiter: Double,
        priceGasolinePerLiter: Double,
        priceEthanolPerLiter: Double
    ) {
        val car = Car(
            vehicle,
            kmGasolinePerLiter,
            kmEthanolPerLiter,
            priceGasolinePerLiter,
            priceEthanolPerLiter,
            ""
        )

        viewModelScope.launch {
            carSaveState.value = saveCarUseCase.save(car)
        }
    }
}