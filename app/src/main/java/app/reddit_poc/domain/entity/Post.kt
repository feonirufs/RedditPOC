package app.reddit_poc.domain.entity

import kotlinx.serialization.Serializable

@Serializable data class Post(
    val title: String,
    val subredditNamePrefixed: String = "",
    val body: String,
    val downs: Int = 0,
    val ups: Int = 0,
    val commentsCount: Int = 0,
    val author: String,
    val createdUtc: Long,
    val created: Long,
    val url: String,
    val thumbnail: String?,
    var after: String?
)