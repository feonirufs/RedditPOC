package app.re_eddit.api.response.post

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ReplyResponse(
    val kind: String,
    val data: RepliesData
)