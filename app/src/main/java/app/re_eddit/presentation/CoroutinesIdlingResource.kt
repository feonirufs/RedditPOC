package app.re_eddit.presentation

import androidx.test.espresso.idling.CountingIdlingResource


object CoroutinesIdlingResource {
    private val idlingResourceName = "COROUTINES_IDLING_RESOURCE"
    @JvmField val idlingResource = CountingIdlingResource(idlingResourceName)
}