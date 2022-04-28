package app.re_eddit.domain.entity

data class Post(
    val title: String,
    val subredditNamePrefixed: String = "",
    val domain: String = "",
    val isRedditMediaDomain: Boolean,
    val body: String,
    val downs: String = "0",
    val ups: String = "",
    val commentsCount: String = "0",
    val postInfo: String,
    val url: String,
    val thumbnail: String?,
    var after: String?
)