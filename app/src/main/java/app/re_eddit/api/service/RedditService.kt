package app.re_eddit.api.service

import app.re_eddit.api.response.post.CommentResponse
import app.re_eddit.api.response.topic.TopicResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RedditService {
    @GET("/r/brasil.json")
    suspend fun getTopicData(
        @Query("limit") limit: Int,
        @Query("after") after: String?
    ): TopicResponse

    @GET("{postUrl}")
    suspend fun getSinglePostData(@Path("postUrl", encoded = true) postUrl: String, @Query("raw_json") raw_json: Int): List<CommentResponse>
}