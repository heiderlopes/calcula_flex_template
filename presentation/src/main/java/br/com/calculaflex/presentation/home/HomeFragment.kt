package br.com.calculaflex.presentation.home

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import br.com.calculaflex.presentation.R
import br.com.calculaflex.data.remote.datasource.AppRemoteFirebaseDataSourceImpl
import br.com.calculaflex.data.repository.AppRepositoryImpl
import br.com.calculaflex.domain.entity.DashboardItem
import br.com.calculaflex.domain.entity.RequestState
import br.com.calculaflex.domain.usecases.GetDashboardMenuUseCase
import br.com.calculaflex.extensions.startDeeplink
import br.com.calculaflex.presentation.base.auth.BaseAuthFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
class HomeFragment : BaseAuthFragment() {

    override val layout = R.layout.fragment_home

    private lateinit var rvHomeDashboard: RecyclerView

    private val homeViewModel: HomeViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerBackPressedAction()

        setUpView(view)

        registerObserver()
        homeViewModel.getDashboardMenu()
    }

    private fun setUpView(view: View) {
        rvHomeDashboard = view.findViewById(R.id.rvHomeDashboard)
    }

    private fun registerObserver() {
        homeViewModel.dashboardItemsState.observe(viewLifecycleOwner, Observer {
            when (it) {
                is RequestState.Loading -> {
                    showLoading()
                }

                is RequestState.Success -> {
                    setUpMenu(it.data)
                    hideLoading()
                }

                is RequestState.Error -> {
                    hideLoading()
                    showMessage(it.throwable.message)
                }
            }
        })
    }

    private fun setUpMenu(items: List<DashboardItem>) {
        rvHomeDashboard.adapter = HomeAdapter(items, this::clickItem)
    }

    private fun clickItem(item: DashboardItem) {
        if (item.onDisabledListener == null) {
            when (item.feature) {
                "SIGN_OUT" -> {
                    //chamar o metodo de logout
                }
                else -> {
                    startDeeplink(item.action.deeplink)
                }
            }
        } else {
            item.onDisabledListener?.invoke()
        }
    }


    private fun registerBackPressedAction() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                activity?.finish()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }
}
