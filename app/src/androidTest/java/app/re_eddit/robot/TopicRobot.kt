package app.re_eddit.robot

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import app.re_eddit.ui.main.PostListingActivity
import app.re_eddit.util.RecyclerViewChildrenCountAssertion.Companion.checkItemCount
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.not
import app.re_eddit.R.id as Ids

class TopicRobot {

    fun launchView(activityRule: ActivityTestRule<PostListingActivity>) = apply {
        activityRule.launchActivity(Intent())
    }

    fun checkIfListOfPostIsShowing() = apply {
        with(onView(withId(Ids.post_listing_root))) {
            check(matches(isDisplayed()))
        }

        with(onView(withId(Ids.post_recycler))) {
            check(matches(isDisplayed()))
        }

        with(onView(withId(Ids.progress_bar))) {
            check(matches(not(isDisplayed())))
        }

    }

    fun checkIfOnlyOnePostIsShowing() = apply {
        with(onView(withId(Ids.post_listing_root))) {
            check(matches(isDisplayed()))
        }

        with(onView(withId(Ids.post_recycler))) {
            check(matches(isDisplayed()))
        }

        with(onView(withId(Ids.progress_bar))) {
            check(matches(not(isDisplayed())))
        }

        onView(
            CoreMatchers.allOf(
                withId(Ids.post_recycler),
                isDescendantOfA(withId(Ids.post_listing_root))
            )
        ).check(checkItemCount(1))
    }

    fun checkIfOnlyEmptyListMessageIsShowing() = apply {
        with(onView(withId(Ids.post_listing_root))) {
            check(matches(isDisplayed()))
        }

        with(onView(withId(Ids.progress_bar))) {
            check(matches(not(isDisplayed())))
        }

        onView(withId(Ids.error_text))
            .check(matches(withText("Não temos nenhum post para você :(")))
            .check(matches(isDisplayed()))

        with(onView(withId(Ids.post_recycler))) {
            check(matches(not(isDisplayed())))
        }
    }

    fun checkIfOnlyErrorMessageIsShowing() = apply {
        with(onView(withId(Ids.post_listing_root))) {
            check(matches(isDisplayed()))
        }

        with(onView(withId(Ids.progress_bar))) {
            check(matches(not(isDisplayed())))
        }

        onView(withId(Ids.error_text))
            .check(matches(withText("Não consegui carregar nenhum post :(")))
            .check(matches(isDisplayed()))

        with(onView(withId(Ids.post_recycler))) {
            check(matches(not(isDisplayed())))
        }
    }
}