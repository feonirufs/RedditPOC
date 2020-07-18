package app.reddit_poc.api.response.topic

import app.reddit_poc.api.response.topic.TopicDataResponse

data class TopicResponse(
    val kind: String = "",
    val data: TopicDataResponse?
)