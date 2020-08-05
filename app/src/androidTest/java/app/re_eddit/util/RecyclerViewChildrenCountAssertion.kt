package app.re_eddit.util

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import org.hamcrest.CoreMatchers.`is`

class RecyclerViewChildrenCountAssertion private constructor(private val expected: Int) : ViewAssertion {

    override fun check(view: View?, noViewFoundException: NoMatchingViewException?) {
        if (noViewFoundException != null) {
            throw noViewFoundException
        }

        val recyclerView = view as RecyclerView

        val actualItemCount = recyclerView.adapter?.itemCount

        assertThat(actualItemCount, `is`<Int>(expected))
    }

    companion object {
        fun checkItemCount(expectedCount: Int): RecyclerViewChildrenCountAssertion {
            return RecyclerViewChildrenCountAssertion(expectedCount)
        }
    }

}