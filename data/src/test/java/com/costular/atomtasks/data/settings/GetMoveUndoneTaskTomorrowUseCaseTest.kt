package com.costular.atomtasks.data.settings

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.google.testing.junit.testparameterinjector.TestParameter
import com.google.testing.junit.testparameterinjector.TestParameterInjector
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(TestParameterInjector::class)
class GetMoveUndoneTaskTomorrowUseCaseTest {

    private lateinit var sut: GetMoveUndoneTaskTomorrowUseCase
    private val settingsRepository: SettingsRepository = mockk(relaxed = true)

    @Before
    fun setUp() {
        sut = GetMoveUndoneTaskTomorrowUseCase(settingsRepository)
    }

    @Test
    fun `should expose what repository returns`(
        @TestParameter expected: Boolean,
    ) = runTest {
        every { settingsRepository.observeMoveUndoneTaskTomorrow() } returns flowOf(expected)

        sut.invoke(Unit)
        sut.flow.test {
            assertThat(expectMostRecentItem()).isEqualTo(expected)
            cancelAndIgnoreRemainingEvents()
        }
    }

}
