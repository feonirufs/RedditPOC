package app.re_eddit.api.response.post

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ListCommentResponse(
    val after: String?,
    val before: String?,
    val children: List<RepliesResponse>,
    val modhash: String
)