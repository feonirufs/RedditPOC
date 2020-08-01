package app.re_eddit.api.response.post

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CommentResponse(
    val kind: String = "",
    val data: ListCommentResponse?
)