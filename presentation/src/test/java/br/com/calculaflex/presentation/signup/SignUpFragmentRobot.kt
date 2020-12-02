package br.com.calculaflex.presentation.signup

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import br.com.calculaflex.domain.entity.RequestState
import br.com.calculaflex.domain.usecases.CreateUserUseCase
import br.com.calculaflex.presentation.R
import br.com.calculaflex.presentation.utils.CalculaFlexDataFactory
import br.com.calculaflex.presentation.utils.isDisplayedInToast
import br.com.calculaflex.presentation.utils.scrollAndCheck
import br.com.calculaflex.presentation.utils.scrollAndClick
import br.com.calculaflex.presentation.utils.scrollAndInput
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.AfterClass
import org.junit.Assert
import org.koin.core.context.loadKoinModules
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import java.lang.Exception

@ExperimentalCoroutinesApi
class SignUpFragmentRobot {

    private lateinit var createUserUseCase: CreateUserUseCase
    private val navController = TestNavHostController(ApplicationProvider.getApplicationContext())

    fun launch(robotCommands: SignUpFragmentRobot.() -> Unit) {
        navController.setGraph(R.navigation.main_nav_graph)
        val scenario = launchFragmentInContainer<SignUpFragment>()
        scenario.onFragment {
            navController.setCurrentDestination(R.id.signUpFragment)
            Navigation.setViewNavController(it.requireView(), navController)
            apply(robotCommands)
        }
    }

    fun mockKoin() {
        createUserUseCase = mockk(relaxed = true)
        loadKoinModules(
            module {
                factory(override = true) { createUserUseCase }
            }
        )
    }

    fun mockSuccessRegistration() {
        mockKoin()
        val success = RequestState.Success(CalculaFlexDataFactory.createUser())
        coEvery { createUserUseCase.create(any()) } returns success
    }

    fun mockErrorRegistration() {
        mockKoin()
        coEvery { createUserUseCase.create(any()) } returns RequestState.Error(
            Exception(REGISTRATION_ERROR_MESSAGE)
        )
    }

    fun clickInTerms() {
        R.id.tvTerms.scrollAndClick()
    }

    fun inputUserName() {
        R.id.etEmailSignUp.scrollAndInput("UserName")
    }

    fun inputEmail() {
        R.id.etEmailSignUp.scrollAndInput("email@email.com")
    }

    fun inputPhone() {
        R.id.etPhoneSignUp.scrollAndInput("11934567891")
    }

    fun inputPassword() {
        R.id.etPasswordSignUp.scrollAndInput("password123")
    }

    fun checkTerms() {
        R.id.cbTermsSignUp.scrollAndCheck()
    }

    fun clickInSignUp() {
        R.id.btCreateAccount.scrollAndClick()
    }

    fun checkErrorMessageIsDisplayed() {
        REGISTRATION_ERROR_MESSAGE.isDisplayedInToast()
    }

    fun checkTermsIsCalled() {
        Assert.assertTrue(navController.currentDestination?.id == R.id.termsFragment)
    }

    fun checkHomeIsCalled() {
        Assert.assertTrue(navController.currentDestination?.id == R.id.homeFragment)
    }

    companion object {
        private const val REGISTRATION_ERROR_MESSAGE = "erro no cadastro"
    }
}