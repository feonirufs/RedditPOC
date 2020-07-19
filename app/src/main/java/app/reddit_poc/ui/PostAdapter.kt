package app.reddit_poc.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.reddit_poc.R
import app.reddit_poc.domain.topic.Post
import kotlinx.android.synthetic.main.single_post_view.view.*

class PostAdapter : RecyclerView.Adapter<PostAdapter.ViewHolder>() {
    var list: List<Post> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_post_view, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    inner class ViewHolder (view: View): RecyclerView.ViewHolder(view) {
        fun bind(post: Post) {
            itemView.userDateTx.text = "u/${post.author} . 4mo" //TODO SETUP DATE RIGHT
            itemView.postTitleTx.text = post.title
            itemView.upTx.text = post.ups.toString()
            itemView.downTx.text = post.downs.toString()
            itemView.commentsTx.text = post.commentsCount.toString()
        }
    }
}