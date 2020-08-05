package app.re_eddit.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {
    fun launch(block: suspend CoroutineScope.() -> Unit): Job =
        ViewModelIdlingResource.idlingResource.increment().let {
            viewModelScope.launch(block = block).also {
                it.invokeOnCompletion { ViewModelIdlingResource.idlingResource.decrement() }
            }
        }
}
