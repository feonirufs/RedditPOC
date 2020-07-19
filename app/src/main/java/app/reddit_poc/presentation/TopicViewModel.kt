package app.reddit_poc.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.reddit_poc.domain.topic.Post
import app.reddit_poc.domain.topic.TopicRepository
import app.reddit_poc.ui.state.UiState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class TopicViewModel(private val topicRepository: TopicRepository) : ViewModel() {

    private val _uiState = MutableLiveData<UiState<List<Post>>>()
    val uiState: LiveData<UiState<List<Post>>> get() = _uiState

    fun getTopic() {
        viewModelScope.launch {
            topicRepository.getAllPostsInTopic()
                .onStart { _uiState.value = UiState.Loading() }
                .onCompletion { exception ->
                    if (exception != null) _uiState.value = UiState.Error(emptyList(), exception.message!!)
                }
                .collect { postList ->
                    _uiState.value = UiState.Data(postList, "")
                }
        }
    }
}