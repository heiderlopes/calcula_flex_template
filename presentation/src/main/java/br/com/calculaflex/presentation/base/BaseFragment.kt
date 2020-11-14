package br.com.calculaflex.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import br.com.calculaflex.domain.entity.RequestState
import br.com.calculaflex.presentation.BuildConfig
import br.com.calculaflex.presentation.R
import br.com.calculaflex.presentation.base.auth.NAVIGATION_KEY
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
abstract class BaseFragment : Fragment() {

    abstract val layout: Int

    private lateinit var loadingView: View


    private val baseViewModel: BaseViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val screenRootView = FrameLayout(requireContext())

        val screenView = inflater.inflate(layout, container, false)

        loadingView = inflater.inflate(R.layout.include_loading, container, false)

        screenRootView.addView(screenView)
        screenRootView.addView(loadingView)

        registerObserver()
        return screenRootView
    }

    private fun registerObserver() {
        baseViewModel.minVersionAppState.observe(viewLifecycleOwner, Observer {
            when (it) {
                is RequestState.Loading -> {
                    showLoading()
                }

                is RequestState.Success -> {
                    hideLoading()
                    if (it.data > BuildConfig.VERSION_CODE) {
                        startUpdateApp()
                    }
                }

                is RequestState.Error -> {
                    NavHostFragment.findNavController(this).navigate(
                        R.id.login_graph, bundleOf(
                            NAVIGATION_KEY to NavHostFragment.findNavController(this).currentDestination?.id
                        )
                    )
                    hideLoading()
                }
            }
        })
    }

    private fun startUpdateApp() {
        val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.updateAppFragment, true)
            .build()

        findNavController().setGraph(R.navigation.update_app_nav_graph)
        findNavController().navigate(R.id.updateAppFragment, null, navOptions)
    }

    override fun onResume() {
        super.onResume()
        baseViewModel.getMinVersion()
    }

    fun showLoading(message: String = "Processando a requisição") {
        loadingView.visibility = View.VISIBLE

        if (message.isNotEmpty())
            loadingView.findViewById<TextView>(R.id.tvLoading).text = message

    }

    fun hideLoading() {
        loadingView.visibility = View.GONE
    }

    fun showMessage(message: String?) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}

