package com.costular.atomtasks.settings

import androidx.compose.ui.test.junit4.createComposeRule
import com.costular.atomtasks.coretesting.ui.ComposeProvider
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.shadows.ShadowLog

@RunWith(RobolectricTestRunner::class)
class SettingsScreenTest : ComposeProvider {

    @get:Rule
    override val composeTestRule = createComposeRule()

    @Before
    @Throws(Exception::class)
    fun setUp() {
        ShadowLog.stream = System.out
    }

    @Test
    fun `given move undone task settings is enabled switch should be enabled too`() {
        givenSettingsScreen(
            SettingsState(
                moveUndoneTasksTomorrowAutomatically = true,
            ),
        )

        settings {
            moveUndoneTasksIsEnabled()
        }
    }

    @Test
    fun `given move undone task settings is disabled switch should be disabled too`() {
        givenSettingsScreen(
            SettingsState(
                moveUndoneTasksTomorrowAutomatically = false,
            ),
        )

        settings {
            moveUndoneTasksIsDisabled()
        }
    }

    private fun givenSettingsScreen(
        state: SettingsState = SettingsState.Empty,
    ) {
        composeTestRule.setContent {
            SettingsScreen(
                state = state,
                navigator = EmptySettingsNavigator,
                onChangeMoveUndoneTask = {},
            )
        }
    }

}
