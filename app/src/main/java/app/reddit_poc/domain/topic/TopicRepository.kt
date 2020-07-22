package app.reddit_poc.domain.topic

import app.reddit_poc.domain.entity.Post
import app.reddit_poc.domain.entity.PostFullPage
import kotlinx.coroutines.flow.Flow

interface TopicRepository {
    fun getAllPostsInTopic(): Flow<List<Post>>
    fun getFullPostData(postLink: String): Flow<PostFullPage>
}