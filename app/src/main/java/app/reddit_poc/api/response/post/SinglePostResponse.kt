package app.reddit_poc.api.response.post

data class SinglePostResponse(
    val response: List<CommentResponse>
)