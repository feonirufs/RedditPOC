package app.re_eddit.ui.main

import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.IdlingRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import androidx.test.rule.ActivityTestRule
import app.re_eddit.FakeWebServiceFactory
import app.re_eddit.api.service.RedditService
import app.re_eddit.app.TestApp
import app.re_eddit.app.di.TestComponent
import app.re_eddit.presentation.CoroutinesIdlingResource
import app.re_eddit.robot.TopicRobot
import app.re_eddit.toJson
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.*
import org.junit.Assert.assertEquals
import org.junit.runner.RunWith
import java.net.HttpURLConnection


@RunWith(AndroidJUnit4::class)
@MediumTest
class PostListingActivityTest {
    @Rule @JvmField val activityRule = ActivityTestRule(
        PostListingActivity::class.java, true, false)

    private lateinit var topicRobot: TopicRobot

    @Before
    fun setUp() {
        val testComponent = ApplicationProvider.getApplicationContext<TestApp>()
            .appComponent() as TestComponent

        testComponent.service =
            apiService

        topicRobot = TopicRobot()
    }

    @Test
    @Throws(Exception::class)
    fun testApplicationName() {
        topicRobot.run {
            launchView(activityRule)
            assertEquals(
                "TestApp",
                activityRule.activity.application.javaClass.simpleName
            )
        }
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

    @Test
    fun shouldShowOnlyEmptyMessage() {
        topicRobot.run {
            dispatchEmptyList()
            launchView(activityRule)
            checkIfOnlyEmptyListMessageIsShowing()
        }
    }

    @Test
    fun shouldShowOnlyErrorMessage() {
        topicRobot.run {
            dispatchException()
            launchView(activityRule)
            checkIfOnlyErrorMessageIsShowing()
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

    private fun dispatchEmptyList() {
        val responseData = "fake-response/post-empty-list.json".toJson()
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(responseData)
        mockWebServer.enqueue(response)
    }

    private fun dispatchException() {
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody("")
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