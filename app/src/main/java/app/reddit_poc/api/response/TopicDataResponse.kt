package app.reddit_poc.api.response

data class TopicDataResponse(
    val modhash: String = "",
    val dist: Int = 0,
    val children: List<TopicPostResponse>,
    val after: String?,
    val before: String?
)