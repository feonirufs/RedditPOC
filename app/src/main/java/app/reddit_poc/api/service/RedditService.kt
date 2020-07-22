package app.reddit_poc.api.service

import app.reddit_poc.api.response.post.CommentResponse
import app.reddit_poc.api.response.topic.TopicResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RedditService {
    @GET("/r/brasil.json")
    suspend fun getTopicData(): TopicResponse

    @GET("{postUrl}")
    suspend fun getSinglePostData(@Path("postUrl", encoded = true) postUrl: String, @Query("raw_json") raw_json: Int): List<CommentResponse>
}