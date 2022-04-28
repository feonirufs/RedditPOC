package app.re_eddit.domain.mapper

import app.re_eddit.api.response.post.CommentResponse
import app.re_eddit.api.response.post.RedditData
import app.re_eddit.api.response.post.RepliesResponse
import app.re_eddit.api.response.post.ReplyResponse
import app.re_eddit.core.ext.toDate
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
    val filteredList = this.filter { it.body != null }

    return filteredList.map { it.toComment(mapper) }
}

private fun RedditData.toComment(hashMap: HashMap<String, Int>) = Comment(
    type = hashMap[this.name]!!,
    commentInfo = "u/" + this.author + " • " + this.created_utc?.toDate(),
    ups = this.ups.toString(),
    downs = this.downs.toString(),
    body = body ?: ""
)

private fun RedditData.toPost() =
    Post(
        title = title,
        subredditNamePrefixed = subreddit_name_prefixed ?: "",
        domain = domain,
        isRedditMediaDomain = is_reddit_media_domain,
        body = this.selftext ?: "",
        downs = downs.toString(),
        ups = ups.toString(),
        commentsCount = num_comments.toString(),
        postInfo = "Posted by u/" + author + " • " + created_utc?.toDate(),
        url = "$permalink.json",
        thumbnail = url_overridden_by_dest,
        after = ""
    )