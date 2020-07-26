package app.reddit_poc.api.repository

import app.reddit_poc.api.response.post.CommentResponse
import app.reddit_poc.api.response.topic.TopicResponse
import app.reddit_poc.api.service.RedditService
import app.reddit_poc.domain.entity.Post
import app.reddit_poc.domain.entity.PostFullPage
import app.reddit_poc.domain.mapper.toDomain
import app.reddit_poc.domain.topic.TopicRepository
import app.reddit_poc.util.MainCoroutineRule
import app.reddit_poc.util.PostFactory.onePost
import app.reddit_poc.util.PostFactory.threePosts
import app.reddit_poc.util.asJson
import assertk.assertThat
import assertk.assertions.isEqualTo
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test

class TopicRepositoryTest {
    @get:Rule
    val rule = MainCoroutineRule()

    private val coroutineScope = rule.testScope

    private val moshi: Moshi = Moshi.Builder().build()

    private val redditApi = mockk<RedditService>()
    private val repository: TopicRepository = TopicRepositoryImpl(redditApi)

    @Test
    fun `should return empty list if api does not return anything`() = runBlocking {
        val emptyTopicResponse = TopicResponse(
            "",
            null
        )

        coEvery { redditApi.getTopicData(limit = any(), after = null) } returns emptyTopicResponse

        lateinit var result: List<Post>

        repository.getAllPostsInTopic(after = "")
            .collect {
                result = it
            }

        coVerify {
            redditApi.getTopicData(limit = any(), after = any())
        }

        assertThat(result).isEqualTo(emptyList())
    }

    @Test
    fun `should return list of one Post if limit passed to api is 1`() = runBlocking {
        val json = "/fake-response/one-post-list.json".asJson()
        val adapter: JsonAdapter<TopicResponse> = moshi.adapter(TopicResponse::class.java)
        val topicResponse = adapter.fromJson(json)!!

        coEvery { redditApi.getTopicData(any(), after = null) } returns topicResponse

        lateinit var result: List<Post>

        repository.getAllPostsInTopic(limit = 1, after = "")
            .collect {
                result = it
            }

        coVerify {
            redditApi.getTopicData(limit = 1, after = any())
        }

        assertThat(result).isEqualTo(onePost)
        assertThat(result.size).isEqualTo(1)
    }

    @Test
    fun `should return list of Post when api return data`() = runBlocking {
        val json = "/fake-response/post-list.json".asJson()
        val adapter: JsonAdapter<TopicResponse> = moshi.adapter(TopicResponse::class.java)
        val topicResponse = adapter.fromJson(json)!!

        coEvery { redditApi.getTopicData(any(), after = null) } returns topicResponse

        lateinit var result: List<Post>

        repository.getAllPostsInTopic(after = "")
            .collect {
                result = it
            }

        coVerify {
            redditApi.getTopicData(limit = any(), after = any())
        }

        assertThat(result).isEqualTo(threePosts)
    }

    @Test
    fun `should return empty list of comments if api does not return comments`() = runBlocking {
        val json = "/fake-response/post-with-no-comments.json".asJson()
        val type = Types.newParameterizedType(
            List::class.java,
            CommentResponse::class.java
        )
        val adapter: JsonAdapter<List<CommentResponse>> = moshi.adapter(type)
        val postResponse = adapter.fromJson(json)!!

        coEvery { redditApi.getSinglePostData(postUrl = "", raw_json = 1) } returns postResponse

        lateinit var result: PostFullPage

        repository.getFullPostData(postLink = "")
            .collect {
                result = it
            }

        coVerify {
            redditApi.getSinglePostData(postUrl = any(), raw_json = 1)
        }

        assertThat(result.comments).isEqualTo(emptyList())
    }

    @Test
    fun `should return post with comments if api return comments`() = runBlocking {
        val json = "/fake-response/post-with-comments.json".asJson()
        val type = Types.newParameterizedType(
            List::class.java,
            CommentResponse::class.java
        )
        val adapter: JsonAdapter<List<CommentResponse>> = moshi.adapter(type)
        val postResponse = adapter.fromJson(json)!!

        coEvery { redditApi.getSinglePostData(postUrl = "", raw_json = 1) } returns postResponse

        lateinit var result: PostFullPage
        val expected = postResponse.toDomain()

        repository.getFullPostData(postLink = "")
            .collect {
                result = it
            }

        coVerify {
            redditApi.getSinglePostData(postUrl = any(), raw_json = 1)
        }

        assertThat(result).isEqualTo(expected)
    }

}