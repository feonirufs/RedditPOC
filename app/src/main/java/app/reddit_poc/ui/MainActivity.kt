package app.reddit_poc.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import app.reddit_poc.R
import app.reddit_poc.api.factory.RepositoryFactory
import app.reddit_poc.presentation.TopicViewModel


class MainActivity : AppCompatActivity() {

    private val repository = RepositoryFactory.topicRepository()
    private val viewModel = TopicViewModel(repository)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.getTopic()
    }
}