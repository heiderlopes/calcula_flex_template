package br.com.calculaflex.presentation.signup

import androidx.lifecycle.Observer
import br.com.calculaflex.domain.entity.RequestState
import br.com.calculaflex.domain.entity.User
import br.com.calculaflex.domain.usecases.CreateUserUseCase
import br.com.calculaflex.presentation.utils.CalculaFlexDataFactory
import br.com.calculaflex.presentation.utils.ViewModelTest
import br.com.calculaflex.presentation.utils.coVerifyOnce
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test
import java.lang.Exception

@ExperimentalCoroutinesApi
class SignUpViewModelTest : ViewModelTest() {

    companion object {
        private const val FAKE_ERROR_MESSAGE = "Erro no cadastro"
    }

    private val createUserUseCase: CreateUserUseCase = mockk(relaxed = true)
    private lateinit var viewModel: SignUpViewModel

    private val createUserObserver: Observer<RequestState<User>> = mockk(relaxed = true)

    @Before
    fun setUp() {
        viewModel = SignUpViewModel(createUserUseCase)
    }

    @Test
    fun `GIVEN success signUp WHEN create user THEN return success`() {
        val newUser = CalculaFlexDataFactory.createNewUser()
        val user = CalculaFlexDataFactory.createUser()
        coEvery { createUserUseCase.create(newUser) } returns RequestState.Success(user)
        viewModel.run {
            newUserState.observeForever(createUserObserver)
            create(newUser.name, newUser.email, newUser.phone, newUser.password)

            coVerifyOnce {
                createUserUseCase.create(newUser)
                createUserObserver.onChanged(RequestState.Success(user))
            }
            newUserState.removeObserver(createUserObserver)
        }
    }

    @Test
    fun `GIVEN failed signUp WHEN create user THEN return error`() {
        val exception = Exception(FAKE_ERROR_MESSAGE)
        coEvery { createUserUseCase.create(any()) } returns RequestState.Error(exception)
        viewModel.run {
            newUserState.observeForever(createUserObserver)
            create("", "", "", "")

            coVerifyOnce {
                createUserObserver.onChanged(RequestState.Error(exception))
            }
            newUserState.removeObserver(createUserObserver)
        }
    }
}