package com.costular.atomtasks.components.createtask

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasParent
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.costular.atomtasks.core_testing.android.ComposeProvider
import com.costular.atomtasks.core_testing.android.Robot

fun com.costular.atomtasks.core_testing.android.ComposeProvider.createTaskExpanded(func: CreateTaskExpandedRobot.() -> Unit) =
    CreateTaskExpandedRobot(composeTestRule)

class CreateTaskExpandedRobot(composeTestRule: ComposeTestRule) : com.costular.atomtasks.core_testing.android.Robot(composeTestRule) {

    private val input by lazy { composeTestRule.onNodeWithTag("CreateTaskInput") }
    private val save by lazy { composeTestRule.onNodeWithTag("CreateTaskExpandedSave") }
    private val chipDate by lazy { composeTestRule.onNodeWithTag("CreateTaskExpandedDate") }
    private val chipReminder by lazy {
        composeTestRule.onNodeWithTag("CreateTaskExpandedReminder")
    }

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
