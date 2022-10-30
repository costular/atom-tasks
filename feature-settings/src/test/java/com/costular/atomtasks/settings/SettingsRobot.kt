package com.costular.atomtasks.settings

import com.costular.commonui.R as R0
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithText
import com.costular.atomtasks.coretesting.ui.ComposeProvider
import com.costular.atomtasks.coretesting.ui.Robot
import com.costular.atomtasks.coretesting.ui.getString

fun ComposeProvider.settings(func: SettingsRobot.() -> Unit) =
    SettingsRobot(composeTestRule).apply(func)

class SettingsRobot(composeTestRule: ComposeTestRule) : Robot(composeTestRule) {

    private val settingsTitle by lazy {
        composeTestRule.onNodeWithText(composeTestRule.getString(R0.string.settings))
    }

    init {
        settingsTitle.assertIsDisplayed()
    }
}
