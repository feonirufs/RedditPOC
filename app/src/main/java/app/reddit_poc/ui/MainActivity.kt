package app.reddit_poc.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import app.reddit_poc.R
import app.reddit_poc.api.factory.RepositoryFactory
import app.reddit_poc.domain.topic.Post
import app.reddit_poc.presentation.TopicViewModel
import app.reddit_poc.ui.state.UiState
import app.reddit_poc.ui.view.MarginItemDecoration
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private val repository = RepositoryFactory.topicRepository()
    private val viewModel = TopicViewModel(repository)

    private val postAdapter = PostAdapter()

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