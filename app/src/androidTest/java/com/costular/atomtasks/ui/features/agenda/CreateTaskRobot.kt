package com.costular.atomtasks.ui.features.agenda

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.ComposeTestRule
import com.costular.atomtasks.ui.base.ComposeProvider
import com.costular.atomtasks.ui.base.Robot

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
