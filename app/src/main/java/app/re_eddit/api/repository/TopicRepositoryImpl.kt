package app.re_eddit.api.repository

import app.re_eddit.api.response.topic.toDomainLayer
import app.re_eddit.api.service.RedditService
import app.re_eddit.core.ext.nullIfBlank
import app.re_eddit.domain.entity.Post
import app.re_eddit.domain.entity.PostFullPage
import app.re_eddit.domain.mapper.toDomain
import app.re_eddit.domain.topic.TopicRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class TopicRepositoryImpl(private val webService: RedditService) : TopicRepository {

    override fun getAllPostsInTopic(limit: Int, after: String): Flow<List<Post>> {
        return flow {
            val apiResult = webService.getTopicData(limit = limit, after = after.nullIfBlank())
            val result = apiResult.data?.let { topicResponse ->
                topicResponse.children.map { it.data.toDomainLayer() }
            }.apply {
                this?.last()?.after = apiResult.data?.after
            }

            result?.let {
                emit(it)
            }?: emptyFlow<List<Post>>()

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