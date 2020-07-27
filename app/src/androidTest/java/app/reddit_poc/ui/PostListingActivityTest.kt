package app.reddit_poc.ui

import androidx.test.espresso.IdlingRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import androidx.test.rule.ActivityTestRule
import app.reddit_poc.FakeWebServiceFactory
import app.reddit_poc.api.service.RedditService
import app.reddit_poc.presentation.CoroutinesIdlingResource
import app.reddit_poc.robot.TopicRobot
import app.reddit_poc.toJson
import app.reddit_poc.ui.main.PostListingActivity
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.*
import org.junit.runner.RunWith
import java.net.HttpURLConnection

@RunWith(AndroidJUnit4::class)
@MediumTest
class PostListingActivityTest {
    @Rule @JvmField val activityRule = ActivityTestRule(
        PostListingActivity::class.java, true, false)

    lateinit var topicRobot: TopicRobot

    @Before
    fun setUp() {
        topicRobot = TopicRobot()
    }

    @Test
    fun shouldShowListOfPost() {
        topicRobot.run {
            dispatchListOfPost()
            launchView(activityRule)
            checkIfListOfPostIsShowing()
        }
    }

    @Test
    fun shouldShowOnlyOnePost() {
        topicRobot.run {
            dispatchOnePost()
            launchView(activityRule)
            checkIfOnlyOnePostIsShowing()
        }
    }

    private fun dispatchListOfPost() {
        val responseData = "fake-response/post-list.json".toJson()
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(responseData)
        mockWebServer.enqueue(response)
    }

    private fun dispatchOnePost() {
        val responseData = "fake-response/one-post-list.json".toJson()
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(responseData)
        mockWebServer.enqueue(response)
    }

    companion object {
        private lateinit var mockWebServer: MockWebServer
        private lateinit var apiService: RedditService

        @BeforeClass
        @JvmStatic
        fun setup() {
            mockWebServer = FakeWebServiceFactory.mockWebServer
            apiService = FakeWebServiceFactory.webService

            IdlingRegistry.getInstance().register(CoroutinesIdlingResource.idlingResource)
        }

        @AfterClass
        @JvmStatic
        fun teardown() {
            mockWebServer.shutdown()

            IdlingRegistry.getInstance().unregister(CoroutinesIdlingResource.idlingResource)
        }
    }
}