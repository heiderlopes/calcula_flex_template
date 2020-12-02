package br.com.calculaflex.presentation.updateapp

import android.content.Intent
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.testing.launchFragmentInContainer
import br.com.calculaflex.presentation.R
import br.com.calculaflex.presentation.utils.click
import br.com.calculaflex.presentation.utils.scrollAndClick
import org.junit.Assert.assertTrue
import org.robolectric.Shadows.shadowOf
import org.robolectric.shadows.ShadowLooper

class UpdateAppFragmentRobot {

    private lateinit var activity : FragmentActivity

    fun launch(robotCommands: UpdateAppFragmentRobot.() -> Unit) {
        val scenario = launchFragmentInContainer<UpdateAppFragment>()
        scenario.onFragment {
            activity = it.requireActivity()
            apply(robotCommands)
        }
    }

    fun clickInUpdate() {
        R.id.btUpdateApp.click()
    }

    fun checkPlayStoreIntent() {
        val intent = shadowOf(activity).peekNextStartedActivity()
        assertTrue(intent.action == Intent.ACTION_VIEW)
        assertTrue(intent.data.toString() == "market://details?id=${activity.packageName}")
    }

}