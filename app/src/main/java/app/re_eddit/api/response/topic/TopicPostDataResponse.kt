package app.re_eddit.api.response.topic

import app.re_eddit.core.ext.toDate
import app.re_eddit.domain.entity.Post
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TopicPostDataResponse(
    val title: String? = "",
    val subreddit_name_prefixed: String? = "",
    val url: String?,
    val selftext: String?,
    val downs: Int = 0,
    val ups: Int = 0,
    val num_comments: Int = 0,
    val author: String? = "",
    val created_utc: Long = 0L,
    val created: Long = 0L,
    val permalink: String?
)

internal fun TopicPostDataResponse.toDomainLayer() =
    Post(
        title = title?: "",
        subredditNamePrefixed = subreddit_name_prefixed?: "",
        body = selftext?: "",
        downs = downs.toString(),
        ups = ups.toString(),
        commentsCount = num_comments.toString(),
        postInfo = "u/" + author + " • " + created_utc.toDate(),
        url = "$permalink.json",
        thumbnail = url,
        after = ""
    )