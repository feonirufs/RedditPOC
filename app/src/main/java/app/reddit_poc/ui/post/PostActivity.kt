package app.reddit_poc.ui.post

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import app.reddit_poc.R
import app.reddit_poc.api.factory.RepositoryFactory
import app.reddit_poc.domain.entity.Post
import app.reddit_poc.domain.entity.PostFullPage
import app.reddit_poc.presentation.TopicViewModel
import app.reddit_poc.ui.common.MarginItemDecoration
import app.reddit_poc.ui.state.UiState
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_post.*


class PostActivity : AppCompatActivity() {
    private val repository = RepositoryFactory.repository
    private val viewModel = TopicViewModel(repository)

    private val postAdapter = PostAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        intent.extras?.let {
            if (intent.hasExtra("url")) {
                viewModel.getFullPost(it.getString("url")!!)
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
                    is UiState.Data -> {
                        hideLoading()
                        onNewData(this.data)
                    }
                    is UiState.Error -> {
                        hideLoading()
                        showToastError(this.error)
                    }
                }
            }
        })
    }

    private fun initUi(post: Post) {
        subRedditTx.text = post.subredditNamePrefixed
        userDateTx.text = "Posted by u/" + post.author + " . 4mo" //TODO SETUP DATE RIGHT
        postTitleTx.text = post.title
        upTx.text = post.ups.toString()
        downTx.text = post.downs.toString()
        commentsTx.text = post.commentsCount.toString()
        if (!post.thumbnail.isNullOrEmpty()) {
            iv_post_thumbnail.visibility = View.VISIBLE
            postBodyTx.visibility = View.GONE
            Glide.with(this).load(post.thumbnail)
                .apply(
                    RequestOptions()
                        .override(Target.SIZE_ORIGINAL))
                .into(iv_post_thumbnail)
        } else {
            iv_post_thumbnail.visibility = View.GONE
            postBodyTx.visibility = View.VISIBLE
            postBodyTx.text = post.body
        }
    }

    private fun onNewData(postFullPage: PostFullPage) {
        postAdapter.list = postFullPage.comments
        initUi(post = postFullPage.post)
    }

    private fun setupPostRecycler() {
        commentsRecycler.run {
            layoutManager = LinearLayoutManager(context)
            adapter = postAdapter
            addItemDecoration(MarginItemDecoration(resources.getDimension(R.dimen.recycler_post_margin).toInt()))
        }
    }

    private fun showLoading() {
        loading.visibility = View.VISIBLE
        postLayout.visibility = View.GONE
        commentsRecycler.visibility = View.GONE
    }

    private fun hideLoading() {
        loading.visibility = View.GONE
        postLayout.visibility = View.VISIBLE
        commentsRecycler.visibility = View.VISIBLE
    }

    private fun showToastError(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

}