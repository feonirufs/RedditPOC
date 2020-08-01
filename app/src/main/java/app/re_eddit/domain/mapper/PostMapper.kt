package app.re_eddit.domain.mapper

import app.re_eddit.api.response.post.CommentResponse
import app.re_eddit.api.response.post.RedditData
import app.re_eddit.api.response.post.RepliesResponse
import app.re_eddit.api.response.post.ReplyResponse
import app.re_eddit.domain.entity.Comment
import app.re_eddit.domain.entity.Post
import app.re_eddit.domain.entity.PostFullPage
import com.google.gson.Gson

fun List<CommentResponse>.toDomain(): PostFullPage {
    val post = this.first().data!!.children.first().data.toPost()
    val listComment = this.last().data?.children?.map {
        it.toList()
    }?.flatten()?.toCommentList()
        ?: emptyList()

    return PostFullPage(post, listComment)
}

private fun RepliesResponse.toList(): List<RedditData> {
    val tempList = mutableListOf<RedditData>()
    this.data.formatReplies()

    tempList.add(this.data)

    if (this.data.replies == null) {
        return tempList
    }

    val reply = this.data.replies as ReplyResponse

    return tempList + reply.data.children.map { it.toList() }.flatten()
}

private fun RedditData.formatReplies() {
    val newReplie = when (this.replies) {
        is String -> {
            null
        }
        else -> {
            val json = Gson().toJson(this.replies, Any::class.java)
            Gson().fromJson(json, ReplyResponse::class.java)
        }
    }

    this.apply {
        replies = newReplie
    }
}

private fun List<RedditData>.toCommentList(): List<Comment> {
    val mapper: HashMap<String, Int> = hashMapOf()
    this.forEach { data ->
        val hasParent = mapper.containsKey(data.parent_id)
        if (hasParent) {
            val parent = mapper[data.parent_id]!!
            mapper[data.name!!] = parent + 1
        } else {
            mapper[data.name!!] = 1
        }

    }

    return this.map { it.toComment(mapper) }
}

private fun RedditData.toComment(hashMap: HashMap<String, Int>) = Comment(
    type = hashMap[this.name]!!,
    author = this.author ?: "",
    ups = this.ups,
    downs = this.downs,
    body = body ?: "",
    createdUtc = created_utc ?: 0L
)

private fun RedditData.toPost() =
    Post(
        title = title,
        subredditNamePrefixed = subreddit_name_prefixed ?: "",
        body = this.selftext ?: "",
        downs = downs,
        ups = ups,
        commentsCount = num_comments ?: 0,
        author = author ?: "",
        createdUtc = created_utc ?: 0L,
        created = created ?: 0L,
        url = "$permalink.json",
        thumbnail = url_overridden_by_dest,
        after = ""
    )