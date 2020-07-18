package app.reddit_poc.api.service

import app.reddit_poc.api.response.topic.TopicResponse
import retrofit2.http.GET

interface RedditService {
    @GET("PersonalFinanceCanada.json")
    suspend fun getTopicData(): TopicResponse
}