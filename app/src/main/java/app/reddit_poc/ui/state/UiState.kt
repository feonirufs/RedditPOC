package app.reddit_poc.ui.state

sealed class UiState<T> {
    class Loading<T> : UiState<T>()
    data class Data<T>(val data: T) : UiState<T>()
    data class Error<T>(val error: String) : UiState<T>()
}