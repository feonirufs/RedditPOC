package app.re_eddit.domain.topic

import app.re_eddit.domain.entity.Post
import app.re_eddit.domain.entity.PostFullPage
import kotlinx.coroutines.flow.Flow

interface TopicRepository {
    fun getAllPostsInTopic(limit: Int = 10, after: String): Flow<List<Post>>
    fun getFullPostData(postLink: String): Flow<PostFullPage>
}