package app.re_eddit.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.re_eddit.domain.entity.Post
import app.re_eddit.domain.topic.TopicRepository
import app.re_eddit.ui.state.UiState
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.flow.onStart

class TopicViewModel(private val topicRepository: TopicRepository) : BaseViewModel() {

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
                    }.onEmpty {
                        _topicUiState.value = UiState.Data(emptyList<Post>())
                    }
                    .collect { postList ->
                        _topicUiState.value = UiState.Data(postList)
                        after = postList.last().after ?: ""
                    }
                isFetching = false
            }
        }
    }

    fun getFullPost(postUrl: String) {
        launch {
            topicRepository.getFullPostData(postUrl)
                .onStart { _postUiState.value = UiState.Loading }
                .catch {
                    _postUiState.value = UiState.Error("Não consegui carregar o post :(")
                }
                .collect { fullPost ->
                    _postUiState.value = UiState.Data(fullPost)
                }
        }
    }
}