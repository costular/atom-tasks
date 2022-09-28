package com.costular.atomtasks.data.settings

import com.google.testing.junit.testparameterinjector.TestParameter
import com.google.testing.junit.testparameterinjector.TestParameterInjector
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(TestParameterInjector::class)
class SetMoveUndoneTaskTomorrowUseCaseTest {

    lateinit var sut: SetMoveUndoneTaskTomorrowUseCase
    private val settingsRepository: SettingsRepository = mockk(relaxed = true)

    @Before
    fun setUp() {
        sut = SetMoveUndoneTaskTomorrowUseCase(settingsRepository)
    }

    @Test
    fun `should set move undone tasks through repository given some input`(
        @TestParameter expected: Boolean,
    ) = runTest {
        sut.executeSync(SetMoveUndoneTaskTomorrowUseCase.Params(expected))

        coVerify { settingsRepository.setMoveUndoneTaskTomorrow(expected) }
    }

}
