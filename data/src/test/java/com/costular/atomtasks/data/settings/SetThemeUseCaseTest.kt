package com.costular.atomtasks.data.settings

import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

class SetThemeUseCaseTest {
    private val testScope = TestCoroutineScope()

    private val repository: SettingsRepository = mockk(relaxed = true)

    lateinit var sut: SetThemeUseCase

    @Before
    fun setUp() {
        sut = SetThemeUseCase(repository)
    }

    @Test
    fun `should store theme in preferences when change theme`() =
        testScope.runBlockingTest {
            val theme = Theme.Dark

            sut.executeSync(SetThemeUseCase.Params(theme))

            coVerify { repository.setTheme(theme) }
        }
}
