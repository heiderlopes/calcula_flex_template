package br.com.calculaflex.presentation.terms

import android.webkit.WebView
import androidx.fragment.app.testing.launchFragmentInContainer
import br.com.calculaflex.presentation.R
import junit.framework.Assert.assertTrue
import org.robolectric.Shadows.shadowOf

class TermsFragmentRobot {

    private lateinit var webView: WebView

    fun launch(robotCommands: TermsFragmentRobot.() -> Unit) {
        val scenario = launchFragmentInContainer<TermsFragment>()
        scenario.onFragment {
            webView = it.requireView().findViewById(R.id.wvTerms)
            apply(robotCommands)
        }
    }

    fun checkLoadUrl() {
        val loadUrl = shadowOf(webView).lastLoadedUrl
        assertTrue(loadUrl == "https://calcula-flex-f8d41.web.app")
    }

}