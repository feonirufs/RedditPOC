package app.reddit_poc.api.response.topic


data class TopicDataResponse(
    val modhash: String = "",
    val dist: Int = 0,
    val children: List<TopicPostResponse>?,
    val after: String? = "",
    val before: String? = ""
)