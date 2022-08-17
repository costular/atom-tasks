package com.costular.atomtasks.agenda

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.costular.atomtasks.core_testing.ui.ComposeProvider
import com.costular.atomtasks.core_testing.ui.Robot

fun ComposeProvider.taskActions(func: TaskActionRobot.() -> Unit) =
    TaskActionRobot(composeTestRule).apply(func)

class TaskActionRobot(composeTestRule: ComposeTestRule) : Robot(composeTestRule) {

    private val taskActionTitle by lazy {
        composeTestRule.onNodeWithTag("TaskActionTitle")
    }
    private val taskActionDone by lazy {
        composeTestRule.onNodeWithTag("TaskActionDone")
    }
    private val taskActionUndone by lazy {
        composeTestRule.onNodeWithTag("TaskActionUndone")
    }
    private val taskActionDelete by lazy {
        composeTestRule.onNodeWithTag("TaskActionDelete")
    }

    init {
        taskActionTitle.assertIsDisplayed()
    }

    fun markAsDone() {
        taskActionDone.assertIsDisplayed()
            .performClick()
    }

    fun markAsUndone() {
        taskActionUndone.assertIsDisplayed()
            .performClick()
    }

    fun delete() {
        taskActionDone.assertIsDisplayed()
            .performClick()
    }
}
