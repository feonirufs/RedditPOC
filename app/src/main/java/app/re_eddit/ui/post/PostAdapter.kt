package app.re_eddit.ui.post

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.RecyclerView
import app.re_eddit.R
import app.re_eddit.domain.entity.Comment
import kotlinx.android.synthetic.main.comment_view.view.*

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
            itemView.user_date.text = comment.commentInfo
            itemView.comment_body.text = comment.body
            itemView.up_count.text = comment.ups
            itemView.down_count.text = comment.downs
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