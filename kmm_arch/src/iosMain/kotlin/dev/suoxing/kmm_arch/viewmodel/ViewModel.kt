package dev.suoxing.kmm_arch.viewmodel

import dev.suoxing.kmm_arch.createViewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.isActive

/**
 * iOS ViewModel 的生命周期需要手动管理。列举一些情况：
 *
 * 1. [viewModelScope] 如果不主动 cancel，则 ViewModel 无法被 deinit，造成内存泄漏
 * 2. SwiftUI 没有好的返回栈处理方式，使得仅在 View 从返回栈 pop 出去时调用 [onCleared]
 * 3. 如果在 onDisapper 时 cancel [viewModelScope]，则 View 从子页面返回时，没有一个活跃的 [viewModelScope]
 *
 * 当前方案的对策如下：
 * 1. view onDisappear 时，cancel 掉 [viewModelScope]
 * 2. view onAppear 时，每次都重新创建一个 [CoroutineScope]，赋值给 [viewModelScope]
 */
actual abstract class ViewModel<T: Any> actual constructor() {
    protected actual var viewModelScope: CoroutineScope = createViewModelScope()
        private set

    /**
     * iOS ViewModel 的生命周期需要手动管理。
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