package br.com.calculaflex.presentation.login

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import br.com.calculaflex.domain.entity.RequestState
import br.com.calculaflex.domain.usecases.LoginUseCase
import br.com.calculaflex.domain.usecases.ResetPasswordUseCase
import br.com.calculaflex.presentation.R
import br.com.calculaflex.presentation.utils.CalculaFlexDataFactory
import br.com.calculaflex.presentation.utils.click
import br.com.calculaflex.presentation.utils.input
import br.com.calculaflex.presentation.utils.isDisplayedInToast
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.AfterClass
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import org.junit.Assert.assertTrue
import org.koin.core.context.stopKoin
import java.lang.Exception

@ExperimentalCoroutinesApi
class LoginFragmentRobot {

    private lateinit var loginUseCase: LoginUseCase
    private lateinit var resetPasswordUseCase: ResetPasswordUseCase
    private val navController = TestNavHostController(ApplicationProvider.getApplicationContext())

    fun launch(robotCommands: LoginFragmentRobot.() -> Unit) {
        navController.setGraph(R.navigation.main_nav_graph)
        val scenario = launchFragmentInContainer<LoginFragment>()
        scenario.onFragment {
            navController.setCurrentDestination(R.id.loginFragment)
            Navigation.setViewNavController(it.requireView(), navController)
            apply(robotCommands)
        }
    }

    fun mockKoin() {
        loginUseCase = mockk(relaxed = true)
        resetPasswordUseCase = mockk(relaxed = true)
        loadKoinModules(
            module {
                factory(override = true) { loginUseCase }
                factory(override = true) { resetPasswordUseCase }
            }
        )
    }

    fun mockSuccessLogin() {
        mockKoin()
        val successUser = RequestState.Success(CalculaFlexDataFactory.createUser())
        coEvery { loginUseCase.doLogin(any()) } returns successUser
    }

    fun mockSuccessResetPassword() {
        mockKoin()
        coEvery { resetPasswordUseCase.resetPassword(any()) }returns
                RequestState.Success(SUCCESS_RESET_PASSWORD_MESSAGE)
    }

    fun mockErrorResetPassword() {
        mockKoin()
        coEvery { resetPasswordUseCase.resetPassword(any()) } returns
                RequestState.Error(Exception(ERROR_RESET_PASSWORD_MESSAGE))
    }

    fun inputLoginUserName() {
       R.id.etEmailLogin.input("UserName")
    }

    fun inputLoginPassword() {
        R.id.etPasswordLogin.input("Password123")
    }

    fun clickInLogin() {
        R.id.btLogin.click()
    }

    fun clickInResetPassword() {
        R.id.tvResetPassword.click()
    }

    fun clickInSignUp() {
        R.id.tvNewAccount.click()
    }

    fun checkHomeIsCalled() {
        assertTrue(navController.currentDestination?.id == R.id.homeFragment)
    }

    fun checkSignUpIsCalled() {
        assertTrue(navController.currentDestination?.id == R.id.signUpFragment)
    }

    fun checkResetSuccessIsDisplayed() {
        SUCCESS_RESET_PASSWORD_MESSAGE.isDisplayedInToast()
    }

    fun checkResetErrorIsDisplayed() {
        ERROR_RESET_PASSWORD_MESSAGE.isDisplayedInToast()
    }

    companion object {
        private const val SUCCESS_RESET_PASSWORD_MESSAGE = "Verifique sua caixa de e-mail"
        private const val ERROR_RESET_PASSWORD_MESSAGE = "Erro ao resetar e-mail"
    }
}