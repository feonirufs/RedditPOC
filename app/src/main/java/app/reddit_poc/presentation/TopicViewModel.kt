package app.reddit_poc.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.reddit_poc.domain.entity.Post
import app.reddit_poc.domain.entity.PostFullPage
import app.reddit_poc.domain.topic.TopicRepository
import app.reddit_poc.ui.state.UiState
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class TopicViewModel(private val topicRepository: TopicRepository) : ViewModel() {

    private val _uiState = MutableLiveData<UiState<List<Post>>>()
    val uiState: LiveData<UiState<List<Post>>> get() = _uiState

    private val _postUiState = MutableLiveData<UiState<PostFullPage>>()
    val postUiState: LiveData<UiState<PostFullPage>> get() = _postUiState

    fun getTopic() {
        viewModelScope.launch {
            topicRepository.getAllPostsInTopic()
                .onStart { _uiState.value = UiState.Loading() }
                .catch {
                    _uiState.value = UiState.Error("Nao foi possível carregar os posts!")
                }
                .collect { postList ->
                    _uiState.value = UiState.Data(postList)
                }
        }
    }

    fun getFullPost(postUrl: String) {
        viewModelScope.launch {
            topicRepository.getFullPostData(postUrl)
                .onStart { _postUiState.value = UiState.Loading() }
                .catch { e ->
                    e.printStackTrace()
                    _postUiState.value = UiState.Error("Nao foi possível carregar os posts!")
                }
                .collect { fullPost ->
                    _postUiState.value = UiState.Data(fullPost)
                }
        }
    }
}