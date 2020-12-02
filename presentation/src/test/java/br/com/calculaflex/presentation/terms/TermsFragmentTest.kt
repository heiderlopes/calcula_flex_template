package br.com.calculaflex.presentation.terms

import br.com.calculaflex.presentation.utils.RobolectricUnitTest
import org.junit.Test

class TermsFragmentTest : RobolectricUnitTest(){

    private fun robot(func: TermsFragmentRobot.() -> Unit) = TermsFragmentRobot()
        .apply { func() }

    @Test
    fun `WHEN open terms THEN load url`() {
        robot {
            launch {
                checkLoadUrl()
            }
        }
    }
}
