package br.com.calculaflex.presentation.home

import android.content.Intent
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import br.com.calculaflex.domain.entity.RequestState
import br.com.calculaflex.domain.usecases.GetDashboardMenuUseCase
import br.com.calculaflex.domain.usecases.GetUserLoggedUseCase
import br.com.calculaflex.presentation.R
import br.com.calculaflex.presentation.utils.CalculaFlexDataFactory
import br.com.calculaflex.presentation.utils.matcher.RecyclerViewMatcher
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import org.robolectric.Shadows

@ExperimentalCoroutinesApi
class HomeFragmentRobot {

    private val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
    private lateinit var activity : FragmentActivity
    private lateinit var getDashboardMenuUseCase: GetDashboardMenuUseCase
    private lateinit var getUserLoggedUseCase: GetUserLoggedUseCase
    private val menu = CalculaFlexDataFactory.getDashboardMenu()

    fun launch(robotCommands: HomeFragmentRobot.() -> Unit) {
        navController.setGraph(R.navigation.main_nav_graph)
        val scenario = launchFragmentInContainer<HomeFragment>(themeResId = R.style.AppTheme)
        scenario.onFragment {
            activity = it.requireActivity()
            Navigation.setViewNavController(it.requireView(), navController)
            it.requireView().findViewById<RecyclerView>(R.id.rvHomeDashboard).apply {
                measure(0, 0)
                layout(0, 0, 400, 800)
            }
            apply(robotCommands)
        }
    }

    fun mockKoin() {
        getDashboardMenuUseCase = mockk(relaxed = true)
        getUserLoggedUseCase = mockk(relaxed = true)
        loadKoinModules(
            module {
                factory(override = true) { getDashboardMenuUseCase }
                factory(override = true) { getUserLoggedUseCase }
            }
        )
        mockUser()
    }

    private fun mockUser() {
        coEvery { getUserLoggedUseCase.getUserLogged() } returns RequestState.Success(
            CalculaFlexDataFactory.createUser()
        )
    }

    fun mockSuccessDashboard() {
        mockKoin()
        coEvery { getDashboardMenuUseCase.getDashboardMenu() } returns RequestState.Success(menu)
    }

    fun checkList() {
        getViewHolderReference(0).check(descendantAssertion(menu.items[0].label))
        getViewHolderReference(1).check(descendantAssertion(menu.items[1].label))
        getViewHolderReference(2).check(descendantAssertion(menu.items[2].label))
    }

    fun checkFirstDeepLink() {
        val intent = Shadows.shadowOf(activity).peekNextStartedActivity()
        Assert.assertTrue(intent.action == Intent.ACTION_VIEW)
        Assert.assertTrue(intent.data.toString() == menu.items[0].action.deeplink)

    }

    fun clickInFirstItem() {
        onView(withId(R.id.rvHomeDashboard))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<HomeAdapter.ViewHolder>(
                    0, click()
                )
            )
    }

    private fun getViewHolderReference(position: Int) =
        onView(RecyclerViewMatcher.withRecyclerView(R.id.rvHomeDashboard).atPosition(position))

    private fun descendantAssertion(testingText: String): ViewAssertion? {
        return ViewAssertions.matches(ViewMatchers.hasDescendant(ViewMatchers.withText(testingText)))
    }

}