package app.re_eddit.ui.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.re_eddit.MainApp
import app.re_eddit.R
import app.re_eddit.domain.entity.Post
import app.re_eddit.presentation.TopicViewModel
import app.re_eddit.ui.common.MarginItemDecoration
import app.re_eddit.ui.post.PostActivity
import app.re_eddit.ui.state.UiState
import kotlinx.android.synthetic.main.activity_main.*


class PostListingActivity : AppCompatActivity() {
    private lateinit var viewModel: TopicViewModel

    private val postAdapter =
        PostListingAdapter(::onPostClicked, ::onMediaClicked)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = (application as MainApp).appComponent().viewModel

        setupPostRecycler()
        initObservers()
    }

    private fun initObservers() {
        viewModel.topicUiState.observe(this, Observer { state ->
            when (state) {
                is UiState.Loading -> {
                    showLoading()
                }
                is UiState.Data<*> -> {
                    hideLoading()
                    onNewData(posts = state.data as List<Post>)
                }
                is UiState.Error -> {
                    hideLoading()
                    showError(state.error)
                }
            }
        })

        viewModel.getPostsFromTopic()
    }

    private fun onNewData(posts: List<Post>) {
        if (posts.isEmpty()) {
            error_text.visibility = View.VISIBLE
            post_recycler.visibility = View.GONE
        } else {
            postAdapter.updatePosts(posts)
            post_recycler.visibility = View.VISIBLE
            error_text.visibility = View.GONE
        }
    }

    private fun onPostClicked(url: String) {
        val intent = Intent(this, PostActivity::class.java).apply {
            putExtra(URL, url)
        }
        startActivity(intent)
    }

    private fun onMediaClicked(url: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(browserIntent)
    }

    private fun setupPostRecycler() {
        post_recycler.run {
            layoutManager = LinearLayoutManager(context)
            adapter = postAdapter
            addItemDecoration(
                MarginItemDecoration(
                    resources.getDimension(R.dimen.recycler_post_margin).toInt()
                )
            )
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (!recyclerView.canScrollVertically(1)) {
                        viewModel.getPostsFromTopic()
                    }
                }
            })
        }
    }

    private fun showLoading() {
        progress_bar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        progress_bar.visibility = View.GONE
    }

    private fun showError(msg: String) {
        error_text.text = msg
        error_text.visibility = View.VISIBLE
        post_recycler.visibility = View.GONE
    }

    companion object {
        const val URL = "url"
    }
}