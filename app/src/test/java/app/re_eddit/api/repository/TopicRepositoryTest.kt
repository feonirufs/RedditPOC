package app.re_eddit.api.repository

import app.re_eddit.api.response.post.CommentResponse
import app.re_eddit.api.response.topic.TopicResponse
import app.re_eddit.api.service.RedditService
import app.re_eddit.domain.entity.Post
import app.re_eddit.domain.entity.PostFullPage
import app.re_eddit.domain.mapper.toDomain
import app.re_eddit.domain.topic.TopicRepository
import app.re_eddit.util.MainCoroutineRule
import app.re_eddit.util.PostFactory.onePost
import app.re_eddit.util.PostFactory.threePosts
import app.re_eddit.util.toJson
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isTrue
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test

class TopicRepositoryTest {
    @get:Rule
    val rule = MainCoroutineRule()

    private val moshi: Moshi = Moshi.Builder().build()

    private val redditApi = mockk<RedditService>()
    private val repository: TopicRepository = TopicRepositoryImpl(redditApi)

    @Test
    fun `should return empty list if api does not return anything`() = runBlocking {
        val emptyTopicResponse = TopicResponse(
            "",
            null
        )

        coEvery { redditApi.getTopicData(limit = any(), after = any()) } returns emptyTopicResponse

        var result = false

        repository.getAllPostsInTopic(after = "")
            .onEmpty {
                result = true
            }.collect {}

        coVerify {
            redditApi.getTopicData(limit = any(), after = any())
        }

        assertThat(result).isTrue()
    }

    @Test
    fun `should return list of one Post if limit passed to api is 1`() = runBlocking {
        val json = "/fake-response/one-post-list.json".toJson()
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
        val json = "/fake-response/post-list.json".toJson()
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
        val json = "/fake-response/post-with-no-comments.json".toJson()
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
        val json = "/fake-response/post-with-comments.json".toJson()
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