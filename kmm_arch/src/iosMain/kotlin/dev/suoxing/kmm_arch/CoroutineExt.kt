package dev.suoxing.kmm_arch

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

actual val ioDispatcher: CoroutineDispatcher = Dispatchers.Default

actual val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default