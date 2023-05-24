package dev.suoxing.kmm_arch.viewmodel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow

expect abstract class ViewModel<T: Any>() {

    protected var viewModelScope: CoroutineScope
        private set

    open fun onCleared()

    protected abstract val _uiStateFlow: MutableStateFlow<T>
}