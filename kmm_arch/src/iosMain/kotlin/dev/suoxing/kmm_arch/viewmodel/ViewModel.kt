package dev.suoxing.kmm_arch.viewmodel

import dev.suoxing.kmm_arch.createViewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

actual abstract class ViewModel<T: Any> actual constructor() {
    protected actual val viewModelScope: CoroutineScope = createViewModelScope()

    actual open fun onCleared() {
        viewModelScope.cancel()
    }

    protected actual abstract val _uiStateFlow: MutableStateFlow<T>

    fun peek(): T = _uiStateFlow.value

    fun collect(onEach: (T) -> Unit) {
        _uiStateFlow
            .onEach { onEach(it) }
            .launchIn(viewModelScope)
    }
}