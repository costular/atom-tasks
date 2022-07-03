package com.costular.atomtasks

import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule

abstract class MviViewModelTest {

    protected val coroutineTestDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()

    private val testScope = TestCoroutineScope(coroutineTestDispatcher)

    protected val dispatcherProvider: com.costular.core.net.DispatcherProvider =
        com.costular.core.net.TestDispatcherProvider(coroutineTestDispatcher)

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule(coroutineTestDispatcher)

    fun testBlocking(block: suspend () -> Unit) {
        testScope.runBlockingTest { block() }
    }
}
