package app.reddit_poc.domain.mapper

import app.reddit_poc.api.response.post.CommentResponse
import app.reddit_poc.api.response.post.RedditData
import app.reddit_poc.api.response.post.RepliesResponse
import app.reddit_poc.api.response.post.ReplyResponse
import app.reddit_poc.domain.entity.Comment
import app.reddit_poc.domain.entity.Post
import app.reddit_poc.domain.entity.PostFullPage
import com.google.gson.Gson


fun List<CommentResponse>.toDomain(): PostFullPage {
    val post = this.first().data.children.first().data.toPost()
    val listComment = this.last().data.children
        .map { it.toList() }
        .flatten()
        .toCommentList()

    return PostFullPage(post, listComment)
}

fun RepliesResponse.toList() : List<RedditData> {
    val tempList = mutableListOf<RedditData>()
    this.data.formatReplies()

    tempList.add(this.data)

    if (this.data.replies == null) {
        return tempList
    }

    val reply = this.data.replies as ReplyResponse

    return tempList + reply.data.children.map { it.toList() }.flatten()
}

fun RedditData.formatReplies() {
    val newReplie = when (this.replies) {
            is String -> { null }
            else -> {
                val json = Gson().toJson(this.replies, Any::class.java)
                Gson().fromJson(json, ReplyResponse::class.java)
            }
        }

    this.apply {
        replies = newReplie
    }
}

fun List<RedditData>.toCommentList(): List<Comment> {
    val mappper: HashMap<String, Int> = hashMapOf()
    this.forEach { data ->
        val hasParent = mappper.containsKey(data.parent_id)
        if(hasParent) {
            val parent = mappper[data.parent_id]!!
            mappper[data.name!!] = parent + 1
        } else {
            mappper[data.name!!] = 1
        }

    }

    return this.map { it.toComment(mappper) }
}

fun RedditData.toComment(hashMap: HashMap<String, Int>) = Comment(
    type = hashMap[this.name]!!,
    author = this.author?: "",
    ups = this.ups,
    downs = this.downs,
    body = body?: "",
    createdUtc = created_utc?: 0L
)

internal fun RedditData.toPost() =
    Post(
        title = title,
        subredditNamePrefixed = subreddit_name_prefixed?: "",
        body = this.selftext?: "",
        downs = downs,
        ups = ups,
        commentsCount = num_comments?: 0,
        author = author?: "",
        createdUtc = created_utc?: 0L,
        created = created?: 0L,
        url = "$permalink.json",
        thumbnail = url_overridden_by_dest
    )