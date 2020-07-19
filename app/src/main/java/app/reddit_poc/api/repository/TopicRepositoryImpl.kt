package app.reddit_poc.api.repository

import app.reddit_poc.api.response.topic.toDomainLayer
import app.reddit_poc.api.service.RedditService
import app.reddit_poc.domain.topic.Post
import app.reddit_poc.domain.topic.TopicRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

@ExperimentalCoroutinesApi
class TopicRepositoryImpl(private val webService: RedditService) : TopicRepository {

    override fun getAllPostsInTopic(): Flow<List<Post>> {
        return flow {
            val data = webService.getTopicData()
                .data
                .children
                .map { it.data.toDomainLayer() }

            emit(data)
        }.flowOn(Dispatchers.IO)
    }
}