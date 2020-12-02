package br.com.calculaflex.presentation.updateapp

import br.com.calculaflex.presentation.utils.RobolectricUnitTest
import org.junit.Test

class UpdateAppFragmentTest : RobolectricUnitTest(){

    private fun robot(func: UpdateAppFragmentRobot.() -> Unit) = UpdateAppFragmentRobot()
        .apply { func() }

    @Test
    fun `WHEN click in update THEN open play store`() {
        robot {
            launch {
                clickInUpdate()
                checkPlayStoreIntent()
            }
        }
    }

}