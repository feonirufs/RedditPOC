package app.re_eddit.ui.post

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import app.re_eddit.MainApp
import app.re_eddit.R
import app.re_eddit.domain.entity.Post
import app.re_eddit.domain.entity.PostFullPage
import app.re_eddit.presentation.TopicViewModel
import app.re_eddit.ui.common.MarginItemDecoration
import app.re_eddit.ui.main.PostListingActivity.Companion.URL
import app.re_eddit.ui.state.UiState
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_post.*


class PostActivity : AppCompatActivity() {
    private lateinit var viewModel: TopicViewModel

    private val postAdapter = PostAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        viewModel = (application as MainApp).appComponent().viewModel

        intent.extras?.let {
            if (intent.hasExtra(URL)) {
                viewModel.getFullPost(it.getString(URL)!!)
            }
        }

        initObservers()
        setupPostRecycler()
    }

    private fun initObservers() {
        viewModel.postUiState.observe(this, Observer { state ->
            with(state) {
                when(this) {
                    is UiState.Loading -> {
                        showLoading()
                    }
                    is UiState.Data<*> -> {
                        hideLoading()
                        onNewData(this.data as PostFullPage)
                    }
                    is UiState.Error -> {
                        hideLoading()
                        showError(this.error)
                    }
                }
            }
        })
    }

    private fun initUi(post: Post) {
        label_sub_reddit.text = post.subredditNamePrefixed
        user_date.text = "Posted by u/" + post.author + " . 4mo" //TODO SETUP DATE RIGHT
        post_title.text = post.title
        up_count.text = post.ups.toString()
        down_count.text = post.downs.toString()
        comments_count.text = post.commentsCount.toString()
        if (!post.thumbnail.isNullOrEmpty()) {
            post_thumbnail.visibility = View.VISIBLE
            post_body.visibility = View.GONE
            Glide.with(this).load(post.thumbnail)
                .apply(
                    RequestOptions()
                        .override(Target.SIZE_ORIGINAL))
                .into(post_thumbnail)
        } else {
            post_thumbnail.visibility = View.GONE
            post_body.visibility = View.VISIBLE
            post_body.text = post.body
        }
    }

    private fun onNewData(postFullPage: PostFullPage) {
        initUi(post = postFullPage.post)
        if (postFullPage.comments.isNotEmpty()) {
            postAdapter.list = postFullPage.comments
            comments_recycler.visibility = View.VISIBLE
            error_text.visibility = View.GONE
        } else {
            comments_recycler.visibility = View.GONE
        }
    }

    private fun setupPostRecycler() {
        comments_recycler.run {
            layoutManager = LinearLayoutManager(context)
            adapter = postAdapter
            addItemDecoration(MarginItemDecoration(resources.getDimension(R.dimen.recycler_post_margin).toInt()))
        }
    }

    private fun showLoading() {
        progress_bar.visibility = View.VISIBLE
        post_layout.visibility = View.GONE
        comments_recycler.visibility = View.GONE
    }

    private fun hideLoading() {
        progress_bar.visibility = View.GONE
        post_layout.visibility = View.VISIBLE
        comments_recycler.visibility = View.VISIBLE
    }

    private fun showError(msg: String) {
        error_text.text = msg
        error_text.visibility = View.VISIBLE
        comments_recycler.visibility = View.GONE
        post_layout.visibility = View.GONE
    }

}