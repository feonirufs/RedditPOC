package app.reddit_poc.domain.topic

data class Topic(
    val title: String,
    val subredditNamePrefixed: String = "",
    val downs: Int = 0,
    val ups: Int = 0,
    val commentsCount: Int = 0,
    val author: String,
    val createdUtc: Double,
    val created: Double
)