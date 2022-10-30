package com.costular.atomtasks.settings

import app.cash.turbine.test
import com.costular.atomtasks.coretesting.MviViewModelTest
import com.costular.atomtasks.data.settings.GetThemeUseCase
import com.costular.atomtasks.data.settings.SetThemeUseCase
import com.costular.atomtasks.data.settings.Theme
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlin.time.ExperimentalTime
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Test

@ExperimentalTime
class SettingsViewModelTest : MviViewModelTest() {

    lateinit var sut: SettingsViewModel

    private val getThemeUseCase: GetThemeUseCase = mockk(relaxed = true)
    private val setThemeUseCase: SetThemeUseCase = mockk(relaxed = true)

    @Before
    fun setUp() {
        initialize()
    }

    private fun initialize() {
        sut = SettingsViewModel(
            getThemeUseCase = getThemeUseCase,
            setThemeUseCase = setThemeUseCase,
        )
    }

    @Test
    fun `should expose light theme in state when land on screen given light theme is set`() =
        testBlocking {
            val theme = Theme.Light
            coEvery { getThemeUseCase.flow } returns flowOf(theme)

            initialize()

            sut.state.test {
                assertThat(expectMostRecentItem().theme).isEqualTo(theme)
            }
        }

    @Test
    fun `should expose dark theme in state when land on screen given dark theme is set`() =
        testBlocking {
            val theme = Theme.Dark
            coEvery { getThemeUseCase.flow } returns flowOf(theme)

            initialize()

            sut.state.test {
                assertThat(expectMostRecentItem().theme).isEqualTo(theme)
            }
        }

    @Test
    fun `should update theme when select theme`() = testBlocking {
        val theme = Theme.Light

        sut.setTheme(theme)

        coEvery { setThemeUseCase(SetThemeUseCase.Params(theme)) }
    }
}
