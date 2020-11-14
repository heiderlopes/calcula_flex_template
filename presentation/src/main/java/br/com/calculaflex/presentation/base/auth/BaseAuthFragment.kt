package br.com.calculaflex.presentation.base.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment.findNavController
import br.com.calculaflex.presentation.R
import br.com.calculaflex.domain.entity.RequestState
import br.com.calculaflex.presentation.base.BaseFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel

val NAVIGATION_KEY = "NAV_KEY"

@ExperimentalCoroutinesApi
abstract class BaseAuthFragment : BaseFragment() {

    private val baseAuthViewModel: BaseAuthViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        registerObserver()

        baseAuthViewModel.getUserLogged()

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun registerObserver() {

        baseAuthViewModel.userLogged.observe(viewLifecycleOwner, Observer { result ->
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
