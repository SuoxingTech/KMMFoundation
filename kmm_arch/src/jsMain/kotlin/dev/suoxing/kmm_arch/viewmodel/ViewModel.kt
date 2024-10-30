package dev.suoxing.kmm_arch.viewmodel

import dev.suoxing.kmm_arch.createViewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.isActive

actual abstract class ViewModel<T: Any> actual constructor() {

    protected actual var viewModelScope: CoroutineScope = createViewModelScope()
        private set

    /**
     * JS ViewModel 的生命周期需要手动管理。
     */
    actual open fun onCleared() {
        viewModelScope.cancel()
    }

    open fun onViewAppear(onNewScope: () -> Unit) {
        if (!viewModelScope.isActive) {
            viewModelScope = createViewModelScope()
            onNewScope()
        }
    }

    protected actual abstract val _uiStateFlow: MutableStateFlow<T>

    fun peek(): T = _uiStateFlow.value

    fun collect(onEach: (T) -> Unit) {
        _uiStateFlow
            .onEach { onEach(it) }
            .launchIn(viewModelScope)
    }
}