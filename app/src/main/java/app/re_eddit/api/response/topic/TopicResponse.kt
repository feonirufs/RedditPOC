package app.re_eddit.api.response.topic

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TopicResponse(
    val kind: String? = "",
    val data: TopicDataResponse?
)