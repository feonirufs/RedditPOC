package app.re_eddit.domain.entity

data class Comment(
    val type: Int = 0,
    val commentInfo: String,
    val ups: String,
    val downs: String,
    val body: String
)