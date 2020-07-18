package app.reddit_poc.api.response

data class TopicPostDataResponse(
    val title: String,
    val subreddit_name_prefixed: String = "",
    val downs: Int = 0,
    val ups: Int = 0,
    val num_comments: Int = 0,
    val author: String,
    val created_utc: Double,
    val created: Double
)