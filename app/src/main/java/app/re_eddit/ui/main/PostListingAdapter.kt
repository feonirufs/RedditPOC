package app.re_eddit.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.re_eddit.R
import app.re_eddit.domain.entity.Post
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target.SIZE_ORIGINAL
import kotlinx.android.synthetic.main.single_post_view.view.*

class PostListingAdapter(
    private val clickCallback: (String.() -> Unit),
    private val mediaClickCallback: (String.() -> Unit)
) :
    RecyclerView.Adapter<PostListingAdapter.ViewHolder>() {
    var list = mutableListOf<Post>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_post_view, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position], clickCallback, mediaClickCallback)
    }

    fun updatePosts(posts: List<Post>) {
        with(list) {
            addAll(posts)
        }
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(
            post: Post,
            clickCallback: (String.() -> Unit),
            mediaClickCallback: (String.() -> Unit)
        ) {
            itemView.user_date.text = post.postInfo
            itemView.post_title.text = post.title
            itemView.up_count.text = post.ups
            itemView.down_count.text = post.downs
            itemView.comments_count.text = post.commentsCount
            itemView.setOnClickListener {
                post.url.clickCallback()
            }

            if (post.body.isNotEmpty()) {
                itemView.post_thumbnail.visibility = View.GONE
                itemView.post_body.visibility = View.VISIBLE
                itemView.post_body.text = post.body
            } else {
                itemView.post_body.visibility = View.GONE
                if (post.isRedditMediaDomain) {
                    itemView.post_thumbnail.visibility = View.VISIBLE
                    itemView.media_domain.visibility = View.GONE
                    Glide.with(itemView).load(post.thumbnail)
                        .apply(
                            RequestOptions()
                                .override(SIZE_ORIGINAL)
                        )
                        .into(itemView.post_thumbnail)
                } else {
                    itemView.post_thumbnail.visibility = View.GONE
                    itemView.media_domain.visibility = View.VISIBLE
                    itemView.media_domain.text = post.domain
                    itemView.media_domain.setOnClickListener {
                        post.thumbnail?.mediaClickCallback()
                    }
                }
            }

        }
    }
}