package app.reddit_poc.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.reddit_poc.domain.topic.TopicRepository
import app.reddit_poc.ui.state.UiState
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart

class TopicViewModel(private val topicRepository: TopicRepository) : CoroutinesViewModel() {

    private val _topicUiState = MutableLiveData<UiState>()
    val topicUiState: LiveData<UiState> get() = _topicUiState

    private val _postUiState = MutableLiveData<UiState>()
    val postUiState: LiveData<UiState> get() = _postUiState

    private var after: String = ""
    private var isFetching: Boolean = false

    fun getPostsFromTopic() {
        if (!isFetching) {
            isFetching = true
            launch {
                topicRepository.getAllPostsInTopic(after = after)
                    .onStart { _topicUiState.value = UiState.Loading }
                    .catch {
                        _topicUiState.value = UiState.Error("Não consegui carregar nenhum post :(")
                    }
                    .collect { postList ->
                        _topicUiState.value = UiState.Data(postList)
                        after = postList.last().after ?: ""
                        isFetching = false
                    }
            }
        }
    }

    fun getFullPost(postUrl: String) {
        launch {
            topicRepository.getFullPostData(postUrl)
                .onStart { _postUiState.value = UiState.Loading }
                .catch {
                    _postUiState.value = UiState.Error("Não consegui carregar nenhum post :(")
                }
                .collect { fullPost ->
                    _postUiState.value = UiState.Data(fullPost)
                }
        }
    }
}