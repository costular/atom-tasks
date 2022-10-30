package com.costular.atomtasks.settings

import androidx.compose.ui.test.junit4.createComposeRule
import com.costular.atomtasks.coretesting.ui.ComposeProvider
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.shadows.ShadowLog

@Ignore("Fails on GitHub Actions")
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
    fun `should show the screen title when the user lands on the screen`() {
        givenSettingsScreen()

        settings {
        }
    }

    private fun givenSettingsScreen(state: SettingsState = SettingsState.Empty) {
        composeTestRule.setContent {
            SettingsScreen(state = state, navigator = EmptySettingsNavigator)
        }
    }
}
