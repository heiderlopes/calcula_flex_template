package br.com.calculaflex.presentation.login

import androidx.lifecycle.Observer
import br.com.calculaflex.domain.entity.RequestState
import br.com.calculaflex.domain.entity.User
import br.com.calculaflex.domain.entity.UserLogin
import br.com.calculaflex.domain.usecases.LoginUseCase
import br.com.calculaflex.domain.usecases.ResetPasswordUseCase
import br.com.calculaflex.presentation.utils.CalculaFlexDataFactory
import br.com.calculaflex.presentation.utils.ViewModelTest
import br.com.calculaflex.presentation.utils.coVerifyOnce
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import java.lang.Exception

@ExperimentalCoroutinesApi
class LoginViewModelTest : ViewModelTest(){

    companion object {
        private const val FAKE_PASSWORD = "Password123"
        private const val FAKE_SUCCESS_RESET = "Sucesso no reset de senha"
    }

    private val loginUseCase: LoginUseCase = mockk(relaxed = true)
    private val resetPasswordUseCase: ResetPasswordUseCase = mockk(relaxed = true)
    private lateinit var viewModel: LoginViewModel

    private val userObserver: Observer<RequestState<User>> = mockk(relaxed = true)
    private val resetObserver: Observer<RequestState<String>> = mockk(relaxed = true)

    @Before
    fun setUp() {
        viewModel = LoginViewModel(loginUseCase, resetPasswordUseCase)
    }

    @Test
    fun `GIVEN success login WHEN do login THEN return success`() = runBlockingTest {
        val user = CalculaFlexDataFactory.createUser()
        val userName = user.email
        coEvery { loginUseCase.doLogin(UserLogin(userName, FAKE_PASSWORD)) } returns RequestState.Success(user)

        viewModel.run {
            loginState.observeForever(userObserver)
            doLogin(userName, FAKE_PASSWORD)

            coVerifyOnce {
                loginUseCase.doLogin(UserLogin(userName, FAKE_PASSWORD))
                userObserver.onChanged(RequestState.Success(user))
            }

            loginState.removeObserver(userObserver)
        }
    }

    @Test
    fun `GIVEN failed login WHEN do login THEN return error`() = runBlockingTest {
        val messageError = "ERROR"
        val exception = Exception(messageError)
        coEvery { loginUseCase.doLogin(any()) } returns RequestState.Error(exception)

        viewModel.run {
            loginState.observeForever(userObserver)
            doLogin("", FAKE_PASSWORD)

            coVerifyOnce {
                loginUseCase.doLogin(any())
                userObserver.onChanged(RequestState.Error(exception))
            }

            loginState.removeObserver(userObserver)
        }
    }

    @Test
    fun `GIVEN success reset WHEN do reset password THEN return success`() = runBlockingTest {
        val userName = CalculaFlexDataFactory.createUser().email
        coEvery { resetPasswordUseCase.resetPassword(userName) } returns RequestState.Success(FAKE_SUCCESS_RESET)

        viewModel.run {
            resetPasswordState.observeForever(resetObserver)
            resetPassword(userName)

            coVerifyOnce {
                resetPasswordUseCase.resetPassword(userName)
                resetObserver.onChanged(RequestState.Success(FAKE_SUCCESS_RESET))
            }

            resetPasswordState.removeObserver(resetObserver)
        }
    }

    @Test
    fun `GIVEN failed reset WHEN do reset password THEN return error`() = runBlockingTest {
        val messageError = "ERROR"
        val exception = Exception(messageError)
        coEvery { resetPasswordUseCase.resetPassword(any()) } returns RequestState.Error(exception)

        viewModel.run {
            resetPasswordState.observeForever(resetObserver)
            resetPassword("")

            coVerifyOnce {
                resetPasswordUseCase.resetPassword(any())
                resetObserver.onChanged(RequestState.Error(exception))
            }

            resetPasswordState.removeObserver(resetObserver)
        }
    }
}