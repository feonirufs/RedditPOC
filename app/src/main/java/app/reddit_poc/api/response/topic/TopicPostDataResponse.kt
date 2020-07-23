package app.reddit_poc.api.response.topic

import app.reddit_poc.domain.entity.Post

data class TopicPostDataResponse(
    val title: String = "",
    val subreddit_name_prefixed: String = "",
    val url: String?,
    val selftext: String,
    val downs: Int = 0,
    val ups: Int = 0,
    val num_comments: Int = 0,
    val author: String = "",
    val created_utc: Long = 0L,
    val created: Long = 0L,
    val permalink: String
)

//Maybe that`s better be an mapper
internal fun TopicPostDataResponse.toDomainLayer() =
    Post(
        title = title,
        subredditNamePrefixed = subreddit_name_prefixed,
        body = selftext,
        downs = downs,
        ups = ups,
        commentsCount = num_comments,
        author = author,
        createdUtc = created_utc,
        created = created,
        url = "$permalink.json",
        thumbnail = url,
        after = ""
    )