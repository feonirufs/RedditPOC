package app.reddit_poc.api.repository

import app.reddit_poc.api.response.topic.toDomainLayer
import app.reddit_poc.api.service.RedditService
import app.reddit_poc.core.ext.nullIfBlank
import app.reddit_poc.domain.entity.Post
import app.reddit_poc.domain.entity.PostFullPage
import app.reddit_poc.domain.mapper.toDomain
import app.reddit_poc.domain.topic.TopicRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

@ExperimentalCoroutinesApi
class TopicRepositoryImpl(private val webService: RedditService) : TopicRepository {

    override fun getAllPostsInTopic(after: String): Flow<List<Post>> {
        return flow {
            val apiResult = webService.getTopicData(after = after.nullIfBlank())
            val result = apiResult
                .data
                .children
                .map { it.data.toDomainLayer() }
            result.last().after = apiResult.data.after

            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    override fun getFullPostData(postLink: String): Flow<PostFullPage> {
        return flow {
            val data = webService.getSinglePostData(postUrl = postLink, raw_json = 1)
                .toDomain()
            emit(data)
        }.flowOn(Dispatchers.IO)
    }
}