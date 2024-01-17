package com.costular.atomtasks.core.net

import kotlinx.coroutines.CoroutineDispatcher

interface DispatcherProvider {
    val io: CoroutineDispatcher
    val main: CoroutineDispatcher
    val computation: CoroutineDispatcher
}
