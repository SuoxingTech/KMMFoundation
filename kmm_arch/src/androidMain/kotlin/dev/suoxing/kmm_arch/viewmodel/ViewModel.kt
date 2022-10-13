package dev.suoxing.kmm_arch.viewmodel

import androidx.lifecycle.ViewModel
import dev.suoxing.kmm_arch.createViewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

actual abstract class ViewModel<T: Any> actual constructor(): ViewModel() {
    protected actual val viewModelScope: CoroutineScope = createViewModelScope()

    public actual override fun onCleared() {
        super.onCleared()

        viewModelScope.cancel()
    }

    protected actual abstract val _uiStateFlow: MutableStateFlow<T>
}