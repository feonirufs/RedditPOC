package app.reddit_poc.api.response.post

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RedditData(
    val ups: Int = 0,
    val downs: Int = 0,
    val url_overridden_by_dest: String?,
    val parent_id: String? = "",
    val body: String? = "",
    val selftext: String? = "",
    val name: String? = "",
    var replies: Any?,
    val created_utc: Long? = 0L,
    val title: String = "",
    val subreddit_name_prefixed: String? = "",
    val num_comments: Int? = 0,
    val author: String? = "",
    val created: Long? = 0L,
    val permalink: String? = null
)