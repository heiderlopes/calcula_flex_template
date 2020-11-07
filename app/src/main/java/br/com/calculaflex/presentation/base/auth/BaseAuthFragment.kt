package br.com.calculaflex.presentation.base.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment.findNavController
import br.com.calculaflex.R
import br.com.calculaflex.data.remote.datasource.UserRemoteFakeDataSourceImpl
import br.com.calculaflex.domain.entity.RequestState
import br.com.calculaflex.domain.usecases.GetUserLoggedUseCase
import br.com.calculaflex.presentation.base.BaseFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
abstract class BaseAuthFragment : BaseFragment() {

    val NAVIGATION_KEY = "NAV_KEY"

    private val baseAuthViewModel: BaseAuthViewModel by lazy {
        ViewModelProvider(
            this,
            BaseViewModelFactory(GetUserLoggedUseCase(UserRemoteFakeDataSourceImpl()))
        ).get(BaseAuthViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        registerObserver()

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun registerObserver() {

        baseAuthViewModel.getUserLogged.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is RequestState.Loading -> {
                    showLoading()
                }

                is RequestState.Success -> {
                    hideLoading()
                }

                is RequestState.Error -> {
                    findNavController(this).navigate(
                        R.id.login_graph, bundleOf(
                            NAVIGATION_KEY to findNavController(this).currentDestination?.id
                        )
                    )
                    hideLoading()
                }
            }
        })
    }
}