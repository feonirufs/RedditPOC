package app.re_eddit.domain.entity


data class PostFullPage(
    val post: Post,
    val comments: List<Comment>
)