package br.com.calculaflex.presentation.base.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import br.com.calculaflex.domain.entity.RequestState
import br.com.calculaflex.domain.entity.User
import br.com.calculaflex.domain.usecases.GetUserLoggedUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect

class BaseAuthViewModel(
    private val getUserLoggedUseCase: GetUserLoggedUseCase
) : ViewModel() {

    val getUserLogged = liveData<RequestState<User>>(Dispatchers.IO) {
        emit(RequestState.Loading)
        try {
            getUserLoggedUseCase.getUserLogged().collect {
                emit(it)
            }
        } catch (e: Exception) {
            this.emit(RequestState.Error(e))
        }
    }
}
