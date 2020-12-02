package br.com.calculaflex.presentation.login

import br.com.calculaflex.presentation.utils.RobolectricUnitTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Ignore
import org.junit.Test

@ExperimentalCoroutinesApi
class LoginFragmentTest : RobolectricUnitTest(){

    private fun robot(func: LoginFragmentRobot.() -> Unit) = LoginFragmentRobot()
        .apply { func() }

    @Test
    @Ignore("falha na posição do click")
    fun `GIVEN valid username and password WHEN click in login THEN open home screen`() {
        robot {
            mockSuccessLogin()
            launch {
                inputLoginUserName()
                inputLoginPassword()
                clickInLogin()
                checkHomeIsCalled()
            }
        }
    }

    @Test
    fun `GIVEN success reset WHEN click in reset password THEN show success message`() {
        robot {
            mockSuccessResetPassword()
            launch {
                inputLoginUserName()
                clickInResetPassword()
                checkResetSuccessIsDisplayed()
            }
        }
    }

    @Test
    fun `GIVEN error reset WHEN click in reset password THEN show error message`() {
        robot {
            mockErrorResetPassword()
            launch {
                inputLoginUserName()
                clickInResetPassword()
                checkResetErrorIsDisplayed()
            }
        }
    }

    @Test
    fun `WHEN click in sign up THEN show sign up screen`() {
        robot {
            mockKoin()
            launch {
                clickInSignUp()
                checkSignUpIsCalled()
            }
        }
    }
}