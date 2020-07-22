package app.reddit_poc.ui.post

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.RecyclerView
import app.reddit_poc.R
import app.reddit_poc.domain.entity.Comment
import kotlinx.android.synthetic.main.comment_view.view.*
import kotlinx.android.synthetic.main.single_post_view.view.downTx
import kotlinx.android.synthetic.main.single_post_view.view.upTx
import kotlinx.android.synthetic.main.single_post_view.view.userDateTx


class PostAdapter: RecyclerView.Adapter<PostAdapter.ViewHolder>() {
    var list: List<Comment> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.comment_view, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    inner class ViewHolder (view: View): RecyclerView.ViewHolder(view) {
        fun bind(comment: Comment) {
            itemView.userDateTx.text = "u/" + comment.author + " . 4mo" //TODO SETUP DATE RIGHT
            itemView.commentBody.text = comment.body
            itemView.upTx.text = comment.ups.toString()
            itemView.downTx.text = comment.downs.toString()
            if (comment.type != 1) {
                itemView.divider.visibility = View.VISIBLE
                ConstraintSet().apply {
                    clone(itemView.root)
                    setMargin(R.id.divider, ConstraintSet.START, 20 * comment.type)
                    setMargin(R.id.root, ConstraintSet.TOP, 0)
                    applyTo(itemView.root)
                }
            } else {
                itemView.divider.visibility = View.GONE
            }
        }
    }
}