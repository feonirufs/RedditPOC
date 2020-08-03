package app.re_eddit.api.factory

import app.re_eddit.api.repository.TopicRepositoryImpl
import app.re_eddit.api.service.RedditService
import app.re_eddit.domain.topic.TopicRepository

class RepositoryFactory(private val redditService: RedditService) {
    fun create(): TopicRepository {
        return TopicRepositoryImpl(redditService)
    }
}