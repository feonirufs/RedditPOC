package app.reddit_poc.api.repository

import android.util.Log
import app.reddit_poc.api.response.topic.toDomainLayer
import app.reddit_poc.api.service.RedditService
import app.reddit_poc.domain.topic.Topic
import app.reddit_poc.domain.topic.TopicRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class TopicRepositoryImpl(private val webService: RedditService) : TopicRepository {

    override suspend fun getAllPostsInTopic(): Flow<List<Topic>> {
        return flow {
            val data = webService.getTopicData().data!!.children!!.map { it.data!!.toDomainLayer() }
            emit(data)
        }.catch {
            Log.d("Retrofit", "deu ruim")
        }.flowOn(Dispatchers.IO)
    }

}