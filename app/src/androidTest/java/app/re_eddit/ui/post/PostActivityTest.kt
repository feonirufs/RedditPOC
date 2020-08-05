package app.re_eddit.ui.post

import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.IdlingRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import androidx.test.rule.ActivityTestRule
import app.re_eddit.FakeWebServiceFactory
import app.re_eddit.api.service.RedditService
import app.re_eddit.app.TestApp
import app.re_eddit.app.di.TestComponent
import app.re_eddit.presentation.ViewModelIdlingResource
import app.re_eddit.robot.PostRobot
import app.re_eddit.toJson
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.*
import org.junit.runner.RunWith
import java.net.HttpURLConnection

@RunWith(AndroidJUnit4::class)
@MediumTest
class PostActivityTest {
    @Rule
    @JvmField val activityRule = ActivityTestRule(
        PostActivity::class.java, true, false)

    private lateinit var postRobot: PostRobot

    @Before
    fun setUp() {
        val testComponent = ApplicationProvider.getApplicationContext<TestApp>()
            .appComponent() as TestComponent

        testComponent.service = apiService

        postRobot = PostRobot()
    }

    @Test
    fun shouldShowPostWithComments() {
        postRobot.run {
            dispatchPostWithComments()
            launchView(activityRule)
            checkIfPostIsShowingWithComments()
        }
    }

    @Test
    fun shouldShowPostWithoutComments() {
        postRobot.run {
            dispatchPostWithoutComments()
            launchView(activityRule)
            checkIfHasZeroComments()
        }
    }

    @Test
    fun shouldShowOnlyThreeCommentsWithReplies() {
        postRobot.run {
            dispatchThreeComments()
            launchView(activityRule)
            checkIfOnlyThreeCommentsWithRepliesIsShowing()
        }
    }

    @Test
    fun shouldShowOnlyErrorMessage() {
        postRobot.run {
            dispatchException()
            launchView(activityRule)
            checkIfOnlyErrorMessageIsShowing()
        }
    }

    private fun dispatchPostWithComments() {
        val responseData = "fake-response/post-with-comments.json".toJson()
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(responseData)
        mockWebServer.enqueue(response)
    }

    private fun dispatchThreeComments() {
        val responseData = "fake-response/post-with-three-comments-with-replies.json".toJson()
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(responseData)
        mockWebServer.enqueue(response)
    }

    private fun dispatchPostWithoutComments() {
        val responseData = "fake-response/post-with-no-comments.json".toJson()
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

            IdlingRegistry.getInstance().register(ViewModelIdlingResource.idlingResource)
        }

        @AfterClass
        @JvmStatic
        fun teardown() {
            mockWebServer.shutdown()

            IdlingRegistry.getInstance().unregister(ViewModelIdlingResource.idlingResource)
        }
    }
}