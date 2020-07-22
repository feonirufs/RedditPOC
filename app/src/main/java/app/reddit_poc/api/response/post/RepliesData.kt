package app.reddit_poc.api.response.post

data class RepliesData(
    val children: List<RepliesResponse>
)