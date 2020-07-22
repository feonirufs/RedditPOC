package app.reddit_poc.domain.entity


data class PostFullPage(
    val post: Post,
    val comments: List<Comment>
)