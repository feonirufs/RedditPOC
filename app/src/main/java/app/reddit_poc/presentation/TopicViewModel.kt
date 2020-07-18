package app.reddit_poc.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.reddit_poc.domain.topic.TopicRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class TopicViewModel(private val topicRepository: TopicRepository) : ViewModel() {

    fun getTopic() {
        viewModelScope.launch {
            try {
                topicRepository.getAllPostsInTopic().collect {
                    it.toString()
                }
            } catch (exception: Throwable) {
                exception.printStackTrace()
            }
        }
    }
}