package com.costular.atomtasks

import com.costular.atomtasks.core.net.DispatcherProvider
import com.costular.atomtasks.core.net.TestDispatcherProvider
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule

abstract class MviViewModelTest {

    protected val coroutineTestDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()

    protected val dispatcherProvider: DispatcherProvider =
        TestDispatcherProvider(coroutineTestDispatcher)

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule(coroutineTestDispatcher)

    fun testBlocking(block: suspend () -> Unit) {
        coroutineTestDispatcher.runBlockingTest { block() }
    }
}
