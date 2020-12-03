package br.com.calculaflex.presentation.home

import androidx.lifecycle.Observer
import br.com.calculaflex.domain.entity.DashboardItem
import br.com.calculaflex.domain.entity.RequestState
import br.com.calculaflex.domain.usecases.GetDashboardMenuUseCase
import br.com.calculaflex.presentation.utils.CalculaFlexDataFactory
import br.com.calculaflex.presentation.utils.ViewModelTest
import br.com.calculaflex.presentation.utils.coVerifyOnce
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeViewModelTest : ViewModelTest() {

    companion object {
        private const val FAKE_ERROR_MESSAGE = "Erro ao obter menu"
    }

    private val getDashboardMenuUseCase: GetDashboardMenuUseCase = mockk(relaxed = true)
    private lateinit var viewModel: HomeViewModel

    private val dashboardObserver: Observer<RequestState<List<DashboardItem>>> = mockk(relaxed = true)

    @Before
    fun setUp() {
        viewModel = HomeViewModel(getDashboardMenuUseCase)
    }

    @Test
    fun `GIVEN success dashboard menu WHEN get dashboard THEN return success`() {
        val menu = CalculaFlexDataFactory.getDashboardMenu()
        coEvery { getDashboardMenuUseCase.getDashboardMenu() } returns RequestState.Success(menu)

        viewModel.run {
            dashboardItemsState.observeForever(dashboardObserver)
            getDashboardMenu()

            coVerifyOnce {
                getDashboardMenuUseCase.getDashboardMenu()
                dashboardObserver.onChanged(RequestState.Success(menu.items))
            }

            dashboardItemsState.removeObserver(dashboardObserver)
        }
    }

    @Test
    fun `GIVEN failed dashboard menu WHEN get dashboard THEN return error`() {
        val exception = Exception(FAKE_ERROR_MESSAGE)
        coEvery { getDashboardMenuUseCase.getDashboardMenu() } returns RequestState.Error(exception)

        viewModel.run {
            dashboardItemsState.observeForever(dashboardObserver)
            getDashboardMenu()

            coVerifyOnce {
                dashboardObserver.onChanged(RequestState.Error(exception))
            }

            dashboardItemsState.removeObserver(dashboardObserver)
        }
    }
}