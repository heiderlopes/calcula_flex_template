package br.com.calculaflex.presentation.home

import br.com.calculaflex.presentation.utils.RobolectricUnitTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeFragmentTest : RobolectricUnitTest(){

    private fun robot(func: HomeFragmentRobot.() -> Unit) = HomeFragmentRobot()
        .apply { func() }

    @Test
    fun `GIVEN valid dashboard WHEN load dashboard THEN show dashboard items`() {
        robot {
            mockSuccessDashboard()
            launch {
                checkList()
            }
        }
    }

    @Test
    fun `GIVEN valid dashboard WHEN click in item THEN call deepLink`() {
        robot {
            mockSuccessDashboard()
            launch {
                clickInFirstItem()
                checkFirstDeepLink()
            }
        }
    }
}