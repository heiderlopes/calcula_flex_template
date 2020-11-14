package br.com.calculaflex.presentation.betterfuel

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.com.calculaflex.R
import br.com.calculaflex.data.remote.datasource.AppRemoteFirebaseDataSourceImpl
import br.com.calculaflex.data.remote.datasource.CarRemoteDataSourceImpl
import br.com.calculaflex.data.remote.datasource.UserRemoteFirebaseDataSourceImpl
import br.com.calculaflex.data.repository.AppRepositoryImpl
import br.com.calculaflex.data.repository.CarRepositoryImpl
import br.com.calculaflex.data.repository.UserRepositoryImpl
import br.com.calculaflex.domain.entity.Car
import br.com.calculaflex.domain.repository.CarRepository
import br.com.calculaflex.domain.usecases.GetMinAppVersionUseCase
import br.com.calculaflex.domain.usecases.GetUserLoggedUseCase
import br.com.calculaflex.domain.usecases.SaveCarUseCase
import br.com.calculaflex.presentation.base.BaseFragment
import br.com.calculaflex.presentation.base.BaseViewModel
import br.com.calculaflex.presentation.base.BaseViewModelFactory
import br.com.calculaflex.presentation.base.auth.BaseAuthFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class BetterFuelFragment : BaseAuthFragment() {

    override val layout = R.layout.fragment_better_fuel

    private val betterFuelViewModel: BetterFuelViewModel by lazy {
        ViewModelProvider(
            this,
            BetterFuelViewModelFactory(
                SaveCarUseCase(
                    GetUserLoggedUseCase(
                        UserRepositoryImpl(
                            UserRemoteFirebaseDataSourceImpl(
                                FirebaseAuth.getInstance(),
                                FirebaseFirestore.getInstance()
                            )
                        )
                    ),
                    CarRepositoryImpl(
                        CarRemoteDataSourceImpl(
                            FirebaseFirestore.getInstance()
                        )
                    )
                )
            )
        ).get(BetterFuelViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        betterFuelViewModel.saveCar(
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