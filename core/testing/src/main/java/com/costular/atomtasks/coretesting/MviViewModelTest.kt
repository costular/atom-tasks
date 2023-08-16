package com.costular.atomtasks.core.testing

import com.costular.core.net.DispatcherProvider
import com.costular.atomtasks.core.testing.net.TestDispatcherProvider
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.Rule

abstract class MviViewModelTest {

    protected val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule(testDispatcher)

}
