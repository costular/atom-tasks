package com.costular.atomtasks.settings

import com.costular.commonui.R as R0
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsOff
import androidx.compose.ui.test.assertIsOn
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onChild
import androidx.compose.ui.test.onNodeWithText
import com.costular.atomtasks.coretesting.ui.ComposeProvider
import com.costular.atomtasks.coretesting.ui.Robot
import com.costular.atomtasks.coretesting.ui.getString

fun ComposeProvider.settings(func: SettingsRobot.() -> Unit) = SettingsRobot(composeTestRule)

class SettingsRobot(composeTestRule: ComposeTestRule) : Robot(composeTestRule) {

    private val settingsTitle by lazy {
        composeTestRule.onNodeWithText(composeTestRule.getString(R0.string.settings))
    }
    private val moveUndoneTasksSwitch by lazy {
        composeTestRule
            .onNodeWithText(composeTestRule.getString(R.string.settings_move_undone_tasks_title))
            .onChild()
    }

    init {
        settingsTitle.assertIsDisplayed()
    }

    fun moveUndoneTasksIsEnabled() {
        moveUndoneTasksSwitch.assertIsOn()
    }

    fun moveUndoneTasksIsDisabled() {
        moveUndoneTasksSwitch.assertIsOff()
    }

}
