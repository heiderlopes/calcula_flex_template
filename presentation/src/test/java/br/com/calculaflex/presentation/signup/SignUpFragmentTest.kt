package br.com.calculaflex.presentation.signup

import br.com.calculaflex.presentation.utils.RobolectricUnitTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test

@ExperimentalCoroutinesApi
class SignUpFragmentTest : RobolectricUnitTest() {

    private fun robot(func: SignUpFragmentRobot.() -> Unit) = SignUpFragmentRobot()
        .apply { func() }

    @Test
    fun `WHEN click in terms THEN open terms screen`() {
        robot {
            mockKoin()
            launch {
                clickInTerms()
                checkTermsIsCalled()
            }
        }
    }

    @Test
    fun `GIVEN success registration WHEN click in sign up THEN open home`() {
        robot {
            mockSuccessRegistration()
            launch {
                inputUserName()
                inputEmail()
                inputPhone()
                inputPassword()
                checkTerms()
                clickInSignUp()
                checkHomeIsCalled()
            }
        }
    }

    @Test
    fun `GIVEN error registration WHEN click in sign up THEN show error message`() {
        robot {
            mockErrorRegistration()
            launch {
                inputUserName()
                inputEmail()
                inputPhone()
                inputPassword()
                checkTerms()
                clickInSignUp()
                checkErrorMessageIsDisplayed()
            }
        }
    }
}