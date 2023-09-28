package com.costular.atomtasks.tasks.createtask

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasParent
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.costular.atomtasks.core.testing.ui.ComposeProvider
import com.costular.atomtasks.core.testing.ui.Robot

fun ComposeProvider.createTaskExpanded(
    func: CreateTaskExpandedRobot.() -> Unit,
) = CreateTaskExpandedRobot(composeTestRule).apply(func)

class CreateTaskExpandedRobot(composeTestRule: ComposeTestRule) : Robot(composeTestRule) {

    private val input by lazy { composeTestRule.onNodeWithTag("CreateTaskInput") }
    private val save by lazy { composeTestRule.onNodeWithTag("CreateTaskExpandedSave") }

    init {
        input.assertIsDisplayed()
    }

    fun typeName(text: String) {
        input.performTextInput(text)
    }

    fun hasName(text: String) {
        input.assert(hasText(text))
    }

    fun canSave() {
        input.assertIsDisplayed()
    }

    fun save() {
        save
            .assertIsDisplayed()
            .performClick()
    }

    fun hasDate(date: String) {
        composeTestRule.onNode(hasText(date))
            .assert(hasParent(hasTestTag("CreateTaskExpandedDate")))
            .assertIsDisplayed()
    }

    fun hasReminder(reminder: String) {
        composeTestRule.onNode(hasText(reminder))
            .assert(hasParent(hasTestTag("CreateTaskExpandedReminder")))
            .assertIsDisplayed()
    }
}
