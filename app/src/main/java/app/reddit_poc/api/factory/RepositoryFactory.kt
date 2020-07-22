package app.reddit_poc.api.factory

import app.reddit_poc.api.repository.TopicRepositoryImpl
import app.reddit_poc.domain.topic.TopicRepository

object RepositoryFactory {

    val repository by lazy { topicRepository() }

    private fun topicRepository(): TopicRepository {
        val webService = WebServiceFactory.webService
        return TopicRepositoryImpl(webService)
    }
}