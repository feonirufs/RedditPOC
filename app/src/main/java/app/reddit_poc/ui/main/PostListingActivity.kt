package app.reddit_poc.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import app.reddit_poc.R
import app.reddit_poc.api.factory.RepositoryFactory
import app.reddit_poc.domain.entity.Post
import app.reddit_poc.presentation.TopicViewModel
import app.reddit_poc.ui.common.MarginItemDecoration
import app.reddit_poc.ui.post.PostActivity
import app.reddit_poc.ui.state.UiState
import kotlinx.android.synthetic.main.activity_main.*


class PostListingActivity : AppCompatActivity() {
    private val repository = RepositoryFactory.repository
    private val viewModel = TopicViewModel(repository)

    private val postAdapter =
        PostListingAdapter(::onPostClicked)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupPostRecycler()
        initObservers()
    }

    private fun initObservers() {
        viewModel.uiState.observe(this, Observer { state ->
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

        viewModel.getTopic()
    }

    private fun onNewData(posts: List<Post>) {
        postAdapter.list = posts
    }

    private fun onPostClicked(url: String) {
        val intent = Intent(this, PostActivity::class.java).apply {
            putExtra("url", url)
        }
        startActivity(intent)

    }

    private fun setupPostRecycler() {
        recyclerPost.run {
            layoutManager = LinearLayoutManager(context)
            adapter = postAdapter
            addItemDecoration(MarginItemDecoration(resources.getDimension(R.dimen.recycler_post_margin).toInt()))
        }
    }

    private fun showLoading() {
        loading.visibility = View.VISIBLE
        recyclerPost.visibility = View.GONE
    }

    private fun hideLoading() {
        loading.visibility = View.GONE
        recyclerPost.visibility = View.VISIBLE
    }

    private fun showToastError(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }
}