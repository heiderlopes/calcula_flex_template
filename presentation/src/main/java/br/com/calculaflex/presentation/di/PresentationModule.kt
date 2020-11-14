package br.com.calculaflex.presentation.di

import br.com.calculaflex.data.di.dataModules
import br.com.calculaflex.domain.di.domainModules
import br.com.calculaflex.presentation.base.BaseViewModel
import br.com.calculaflex.presentation.base.auth.BaseAuthViewModel
import br.com.calculaflex.presentation.betterfuel.BetterFuelViewModel
import br.com.calculaflex.presentation.home.HomeViewModel
import br.com.calculaflex.presentation.login.LoginViewModel
import br.com.calculaflex.presentation.signup.SignUpViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private val viewModulModule = module {

    viewModel {
        BaseAuthViewModel(
            getUserLoggedUseCase = get()
        )
    }

    viewModel {
        BaseViewModel(
            getMinAppVersionUseCase = get()
        )
    }


    viewModel {
        BetterFuelViewModel(
            saveCarUseCase = get(),
            calculateBetterFuelUseCase = get()
        )
    }

    viewModel {
        HomeViewModel(
            getDashboardMenuUseCase = get()
        )
    }

    viewModel {
        LoginViewModel(
            loginUseCase = get(),
            resetPasswordUseCase = get()
        )
    }

    viewModel {
        SignUpViewModel(
            createUserUseCase = get()
        )
    }

}

private val presentationsModules = listOf(viewModulModule)

@ExperimentalCoroutinesApi
val featureModules = listOf(domainModules + dataModules + presentationsModules)

