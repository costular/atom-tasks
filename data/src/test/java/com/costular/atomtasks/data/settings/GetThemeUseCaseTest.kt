package com.costular.atomtasks.data.settings

import app.cash.turbine.test
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import kotlin.time.ExperimentalTime
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalTime
class GetThemeUseCaseTest {

    lateinit var sut: GetThemeUseCase

    private val repository: SettingsRepository = mockk(relaxed = true)

    @Before
    fun setUp() {
        sut = GetThemeUseCase(repository)
    }

    @Test
    fun `should return light when invoke use case given light theme is set`() = runTest {
        val theme = Theme.Light
        every { repository.observeTheme() } returns flowOf(theme)

        sut.invoke(Unit)
        sut.flow.test {
            Truth.assertThat(awaitItem()).isEqualTo(theme)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `should return dark when invoke use case given light theme is set`() = runTest {
        val theme = Theme.Dark
        every { repository.observeTheme() } returns flowOf(theme)

        sut.invoke(Unit)
        sut.flow.test {
            Truth.assertThat(awaitItem()).isEqualTo(theme)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `should return system when invoke use case given light theme is set`() = runTest {
        val theme = Theme.System
        every { repository.observeTheme() } returns flowOf(theme)

        sut.invoke(Unit)
        sut.flow.test {
            Truth.assertThat(awaitItem()).isEqualTo(theme)
            cancelAndIgnoreRemainingEvents()
        }
    }
}
