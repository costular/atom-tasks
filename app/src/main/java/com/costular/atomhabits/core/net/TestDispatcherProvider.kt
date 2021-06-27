package com.costular.atomhabits.core.net

import kotlinx.coroutines.CoroutineDispatcher

class TestDispatcherProvider(
    private val testCoroutineDispatcher: CoroutineDispatcher
) : DispatcherProvider {
    override val io: CoroutineDispatcher = testCoroutineDispatcher
    override val main: CoroutineDispatcher = testCoroutineDispatcher
    override val computation: CoroutineDispatcher = testCoroutineDispatcher
}