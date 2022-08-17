package com.costular.atomtasks.agenda

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.costular.atomtasks.core_testing.ui.ComposeProvider
import com.costular.atomtasks.core_testing.ui.Robot

fun ComposeProvider.createTask(func: CreateTaskRobot.() -> Unit) =
    CreateTaskRobot(composeTestRule).apply(func)

class CreateTaskRobot(composeTestRule: ComposeTestRule) : Robot(composeTestRule) {

    private val createTaskInput by lazy {
        composeTestRule.onNodeWithTag("CreateTaskInput")
    }
    private val createTaskSave by lazy {
        composeTestRule.onNodeWithTag("CreateTaskSave")
    }

    init {
        createTaskInput.assertIsDisplayed()
    }

    fun type(input: String) {
        createTaskInput.performTextInput(input)
    }

    fun assertSaveIsDisplayed() {
        createTaskSave.assertIsDisplayed()
    }

    fun save() {
        createTaskSave
            .assertIsDisplayed()
            .performClick()
    }
}
