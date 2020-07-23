package app.reddit_poc.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.reddit_poc.R
import app.reddit_poc.domain.entity.Post
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target.SIZE_ORIGINAL
import kotlinx.android.synthetic.main.single_post_view.view.*

class PostListingAdapter(private val clickCallback: (String.() -> Unit)) : RecyclerView.Adapter<PostListingAdapter.ViewHolder>() {
    var list= mutableListOf<Post>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_post_view, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position], clickCallback)
    }

    fun updatePosts(posts: List<Post>) {
        with(list) {
            addAll(posts)
        }
        notifyDataSetChanged()
    }

    inner class ViewHolder (view: View): RecyclerView.ViewHolder(view) {
        fun bind(post: Post, clickCallback: (String.() -> Unit)) {
            itemView.userDateTx.text = "u/" + post.author + " . 4mo" //TODO SETUP DATE RIGHT
            itemView.postTitleTx.text = post.title
            itemView.upTx.text = post.ups.toString()
            itemView.downTx.text = post.downs.toString()
            itemView.commentsTx.text = post.commentsCount.toString()
            itemView.setOnClickListener {
                post.url.clickCallback()
            }

            if (!post.thumbnail.isNullOrEmpty()) {
                itemView.iv_post_thumbnail.visibility = View.VISIBLE
                itemView.postBodyTx.visibility = View.GONE
                Glide.with(itemView).load(post.thumbnail)
                    .apply(
                        RequestOptions()
                            .override(SIZE_ORIGINAL))
                    .into(itemView.iv_post_thumbnail)
            } else {
                itemView.iv_post_thumbnail.visibility = View.GONE
                itemView.postBodyTx.visibility = View.VISIBLE
                itemView.postBodyTx.text = post.body
            }

        }
    }
}