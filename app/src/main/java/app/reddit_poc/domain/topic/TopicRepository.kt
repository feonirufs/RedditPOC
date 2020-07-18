package app.reddit_poc.domain.topic

import kotlinx.coroutines.flow.Flow

interface TopicRepository {
    suspend fun getAllPostsInTopic(): Flow<List<Topic>>
}