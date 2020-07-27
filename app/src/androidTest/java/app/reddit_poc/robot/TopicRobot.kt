package app.reddit_poc.robot

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import app.reddit_poc.ui.main.PostListingActivity
import app.reddit_poc.util.RecyclerViewItemCountAssertion.Companion.hasItemCount
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.not
import app.reddit_poc.R.id as Ids

class TopicRobot {

    fun launchView(activityRule: ActivityTestRule<PostListingActivity>) = apply {
        activityRule.launchActivity(Intent()).let { Unit }
    }

    fun checkIfListOfPostIsShowing() = apply {
        with(onView(withId(Ids.post_listing_root))) {
            check(matches(isDisplayed()))
        }

        with(onView(withId(Ids.recyclerPost))) {
            check(matches(isDisplayed()))
        }

        with(onView(withId(Ids.loading))) {
            check(matches(not(isDisplayed())))
        }

    }

    fun checkIfOnlyOnePostIsShowing() = apply {
        with(onView(withId(Ids.post_listing_root))) {
            check(matches(isDisplayed()))
        }

        with(onView(withId(Ids.recyclerPost))) {
            check(matches(isDisplayed()))
        }

        with(onView(withId(Ids.loading))) {
            check(matches(not(isDisplayed())))
        }

        onView(
            CoreMatchers.allOf(
                withId(Ids.recyclerPost),
                isDescendantOfA(withId(Ids.post_listing_root))
            )
        ).check(hasItemCount(12))
    }
}