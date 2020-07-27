package app.reddit_poc.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import app.reddit_poc.domain.topic.TopicRepository
import app.reddit_poc.ui.state.UiState
import app.reddit_poc.util.MainCoroutineRule
import app.reddit_poc.util.PostFactory.fullPostWithComments
import app.reddit_poc.util.PostFactory.threePosts
import io.mockk.*
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test

class TopicViewModelTest {
    @get:Rule val instantTaskExecutorRule = InstantTaskExecutorRule()
    @get:Rule val coroutinesTestRule = MainCoroutineRule()

    private val interactor = mockk<TopicRepository>()
    private val viewModel = TopicViewModel(interactor)

    private val topicUiState = mockk<Observer<UiState>>()
    private val postUiState = mockk<Observer<UiState>>()

    @Test
    fun `get list of Post with success from repository`() = coroutinesTestRule.runBlockingTest {
        coEvery { interactor.getAllPostsInTopic(any(), any()) } returns flowOf(threePosts)

        every { topicUiState.onChanged(any()) } just runs

        viewModel.topicUiState.observeForever(topicUiState)

        viewModel.getPostsFromTopic()

        val state = UiState.Data(threePosts)

        coVerify { interactor.getAllPostsInTopic(limit = 10, after = any()) }

        verify { topicUiState.onChanged(state) }
    }

    @Test
    fun `get error state when get exception from repository when fetching list of post`() = coroutinesTestRule.runBlockingTest {
        coEvery { interactor.getAllPostsInTopic(any(), any()) } returns flow { throw Exception() }

        every { topicUiState.onChanged(any()) } just runs

        viewModel.topicUiState.observeForever(topicUiState)

        viewModel.getPostsFromTopic()

        val state = UiState.Error("Não consegui carregar nenhum post :(")

        coVerify { interactor.getAllPostsInTopic(limit = 10, after = any()) }

        verify { topicUiState.onChanged(state) }
    }

    @Test
    fun `should set loading state while fetching list of post`() = coroutinesTestRule.runBlockingTest {
        coEvery { interactor.getAllPostsInTopic(any(), any()) } returns flowOf(threePosts)

        viewModel.topicUiState.observeForever(topicUiState)

        viewModel.getPostsFromTopic()

        val state = UiState.Loading

        coVerify { interactor.getAllPostsInTopic(limit = 10, after = any()) }

        verify { topicUiState.onChanged(state) }
    }

    @Test
    fun `get Post with success from repository`() = coroutinesTestRule.runBlockingTest {
        coEvery { interactor.getFullPostData(any()) } returns flowOf(fullPostWithComments)

        every { postUiState.onChanged(any()) } just runs

        viewModel.postUiState.observeForever(postUiState)

        viewModel.getFullPost(POST_WITH_COMMENT_URL)

        val state = UiState.Data(fullPostWithComments)

        coVerify { interactor.getFullPostData(any()) }

        verify { postUiState.onChanged(state) }
    }

    @Test
    fun `get error state when get exception from repository when fetching full post`() = coroutinesTestRule.runBlockingTest {
        coEvery { interactor.getFullPostData(any()) } returns flow { throw Exception() }

        every { postUiState.onChanged(any()) } just runs

        viewModel.postUiState.observeForever(postUiState)

        viewModel.getFullPost(POST_WITH_COMMENT_URL)

        val state = UiState.Error("Não consegui carregar nenhum post :(")

        coVerify { interactor.getFullPostData(any()) }

        verify { postUiState.onChanged(state) }
    }

    @Test
    fun `should set loading state while fetching full post`() = coroutinesTestRule.runBlockingTest {
        coEvery { interactor.getFullPostData(any()) } returns flowOf(fullPostWithComments)

        viewModel.postUiState.observeForever(postUiState)

        viewModel.getFullPost(POST_WITH_NO_COMMENT_URL)

        val state = UiState.Loading

        coVerify { interactor.getFullPostData(any()) }

        verify { postUiState.onChanged(state) }
    }

    companion object {
        const val POST_WITH_NO_COMMENT_URL = "r/brasil/comments/hx2amb/que_podcasts_você_está_ouvindo_24072020/.json"
        const val POST_WITH_COMMENT_URL = "/r/programming/comments/hxq9ut/mezzano_an_os_written_in_common_lisp_has_released/.json"
    }
}