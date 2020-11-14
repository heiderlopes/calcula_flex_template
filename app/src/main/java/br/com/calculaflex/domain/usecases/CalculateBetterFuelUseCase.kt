package br.com.calculaflex.domain.usecases

import br.com.calculaflex.domain.entity.RequestState
import br.com.calculaflex.domain.entity.enums.FuelType

class CalculateBetterFuelUseCase {

    suspend fun calculate(
        ethanolAverage: Double,
        gasAverage: Double,
        ethanolPrice: Double,
        gasPrice: Double
    ): RequestState<FuelType> {

        val performanceOfMyCar = ethanolAverage / gasAverage
        val priceOfFuelIndice = ethanolPrice / gasPrice

        return if (priceOfFuelIndice <= performanceOfMyCar) {
            RequestState.Success(FuelType.ETHANOL)
        } else {
            RequestState.Success(FuelType.GASOLINE)
        }
    }
}