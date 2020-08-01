package app.re_eddit.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class Comment(
    val type: Int = 0,
    val author: String,
    val ups: Int = 0,
    val downs: Int = 0,
    val body: String,
    val createdUtc: Long
)