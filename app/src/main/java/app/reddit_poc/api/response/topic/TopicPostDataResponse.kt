package app.reddit_poc.api.response.topic

import app.reddit_poc.domain.topic.Topic

data class TopicPostDataResponse(
    val title: String = "",
    val subreddit_name_prefixed: String = "",
    val downs: Int = 0,
    val ups: Int = 0,
    val num_comments: Int = 0,
    val author: String = "",
    val created_utc: Double = 0.0,
    val created: Double = 0.0
)

//Maybe that`s better be an mapper
internal fun TopicPostDataResponse.toDomainLayer() = Topic(
    title = title,
    subredditNamePrefixed = subreddit_name_prefixed,
    downs = downs,
    ups = ups,
    commentsCount = num_comments,
    author = author,
    createdUtc = created_utc,
    created = created
)