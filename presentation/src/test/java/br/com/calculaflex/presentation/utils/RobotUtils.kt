package br.com.calculaflex.presentation.utils

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.supportsInputMethods
import androidx.test.espresso.matcher.ViewMatchers.withId
import junit.framework.Assert.assertTrue
import org.hamcrest.CoreMatchers.allOf
import org.robolectric.shadows.ShadowToast

fun Int.input(text: String) {
    onView(allOf(supportsInputMethods(), withId(this))).perform(replaceText(text))
}

fun Int.scrollAndInput(text: String) {
    onView(allOf(supportsInputMethods(), withId(this))).perform(scrollTo(), replaceText(text))
}

fun Int.scrollAndClick() {
    onView(withId(this)).perform(scrollTo(), ViewActions.click())
}

fun Int.click() {
    onView(withId(this)).perform(ViewActions.click())
}

fun Int.scrollAndCheck() {
    onView(withId(this)).perform(scrollTo(), ViewActions.click())
}

fun String.isDisplayedInToast() {
    assertTrue(ShadowToast.getTextOfLatestToast() == this)
}