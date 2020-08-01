package app.re_eddit.robot

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.rule.ActivityTestRule
import app.re_eddit.R
import app.re_eddit.ui.main.PostListingActivity
import app.re_eddit.ui.post.PostActivity
import app.re_eddit.util.RecyclerViewItemCountAssertion
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.not

class PostRobot {

    fun launchView(activityRule: ActivityTestRule<PostActivity>) = apply {
        activityRule.launchActivity(Intent().apply {
            putExtra(PostListingActivity.URL, URL)
        })
    }

    fun checkIfPostIsShowingWithComments() = apply {
        onView(ViewMatchers.withId(R.id.post_view_root))
            .check(matches(isDisplayed()))

        onView(ViewMatchers.withId(R.id.comments_recycler))
            .check(matches(isDisplayed()))

        onView(ViewMatchers.withId(R.id.post_layout))
            .check(matches(isDisplayed()))

        onView(ViewMatchers.withId(R.id.progress_bar))
            .check(matches(not(isDisplayed())))
    }

    fun checkIfOnlyThreeCommentsWithRepliesIsShowing() = apply {
        onView(ViewMatchers.withId(R.id.post_view_root))
            .check(matches(isDisplayed()))

        onView(ViewMatchers.withId(R.id.comments_recycler))
            .check(matches(isDisplayed()))

        onView(ViewMatchers.withId(R.id.progress_bar))
            .check(matches(not(isDisplayed())))

        onView(
            CoreMatchers.allOf(
                ViewMatchers.withId(R.id.comments_recycler),
                ViewMatchers.isDescendantOfA(ViewMatchers.withId(R.id.post_view_root))
            )
        ).check(RecyclerViewItemCountAssertion.hasItemCount(8))
    }

    fun checkIfHasZeroComments() = apply {
        onView(ViewMatchers.withId(R.id.post_view_root))
            .check(matches(isDisplayed()))

        onView(ViewMatchers.withId(R.id.comments_recycler))
            .check(matches(not(isDisplayed())))

        onView(ViewMatchers.withId(R.id.progress_bar))
            .check(matches(not(isDisplayed())))

        onView(
            CoreMatchers.allOf(
                ViewMatchers.withId(R.id.comments_recycler),
                ViewMatchers.isDescendantOfA(ViewMatchers.withId(R.id.post_view_root))
            )
        ).check(RecyclerViewItemCountAssertion.hasItemCount(0))
    }

    fun checkIfOnlyErrorMessageIsShowing() = apply {
        onView(ViewMatchers.withId(R.id.post_view_root))
            .check(matches(isDisplayed()))

        onView(ViewMatchers.withId(R.id.progress_bar))
            .check(matches(not(isDisplayed())))

        onView(ViewMatchers.withId(R.id.error_text))
            .check(matches(ViewMatchers.withText("Não consegui carregar o post :(")))
            .check(matches(isDisplayed()))

        onView(ViewMatchers.withId(R.id.comments_recycler))
            .check(matches(not(isDisplayed())))

        onView(ViewMatchers.withId(R.id.post_layout))
            .check(matches(not(isDisplayed())))
    }

    companion object {
        const val URL = "r/brasil/comments/i15l6g/uma_época_inteira_na_palma_da_mão/.json"
    }
}