package app.reddit_poc.api.response.post

data class ReplyData(
    val ups: Int = 0,
    val downs: Int = 0,
    val replies: RepliesResponse?,
    val parentId: String,
    val body: String,
    val name: String,
    val created_utc: Long
)