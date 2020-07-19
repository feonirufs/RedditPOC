package app.reddit_poc.domain.topic

import kotlinx.coroutines.flow.Flow

interface TopicRepository {
    fun getAllPostsInTopic(): Flow<List<Post>>
}