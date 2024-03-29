package com.costular.atomtasks.core.testing.net

import com.costular.atomtasks.core.net.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.TestDispatcher

class TestDispatcherProvider(
    testDispatcher: TestDispatcher,
) : DispatcherProvider {
    override val io: CoroutineDispatcher = testDispatcher
    override val main: CoroutineDispatcher = testDispatcher
    override val computation: CoroutineDispatcher = testDispatcher
}
