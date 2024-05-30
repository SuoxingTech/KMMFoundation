package dev.suoxing.kmm_arch

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

actual val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

actual val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default