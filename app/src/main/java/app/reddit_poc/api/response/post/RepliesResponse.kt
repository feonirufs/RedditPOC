package app.reddit_poc.api.response.post

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RepliesResponse(
    val kind: String,
    val data: RedditData
)