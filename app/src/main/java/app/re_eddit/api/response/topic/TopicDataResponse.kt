package app.re_eddit.api.response.topic

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TopicDataResponse(
    val modhash: String? = "",
    val dist: Int? = 0,
    val children: List<TopicPostResponse>,
    val after: String? = "",
    val before: String? = ""
)