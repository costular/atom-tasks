package com.costular.atomtasks.agenda

import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotSelected
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasParent
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onChildAt
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.costular.atomtasks.core_testing.ui.ComposeProvider
import com.costular.atomtasks.core_testing.ui.Robot

fun ComposeProvider.agenda(func: AgendaRobot.() -> Unit) = AgendaRobot(composeTestRule).apply(func)

class AgendaRobot(composeTestRule: ComposeTestRule) : Robot(composeTestRule) {

    private val title by lazy { composeTestRule.onNodeWithTag("AgendaTitle") }
    private val taskList by lazy { composeTestRule.onNodeWithTag("AgendaTaskList") }
    private val nextDay by lazy { composeTestRule.onNodeWithTag("AgendaNextDay") }
    private val prevDay by lazy { composeTestRule.onNodeWithTag("AgendaPrevDay") }
    private val createTask by lazy { composeTestRule.onNodeWithTag("AgendaCreateTask") }

    init {
        title.assertIsDisplayed()
    }

    fun assertDayText(text: String) {
        title.assertTextEquals(text)
    }

    fun goNextDay() {
        nextDay
            .assertIsDisplayed()
            .performClick()
    }

    fun goPrevDay() {
        prevDay
            .assertIsDisplayed()
            .performClick()
    }

    fun goToday() {
        title.performClick()
    }

    infix fun openCreateTask(func: CreateTaskRobot.() -> Unit): CreateTaskRobot {
        createTask
            .assertIsDisplayed()
            .performClick()

        return CreateTaskRobot(composeTestRule).apply(func)
    }

    fun clickOnTask(name: String, func: TaskActionRobot.() -> Unit): TaskActionRobot {
        composeTestRule.onNode(hasText(name))
            .assert(hasParent(hasTestTag("AgendaTaskList")))
            .performClick()

        return TaskActionRobot(composeTestRule).apply(func)
    }

    fun taskHasText(index: Int, text: String) {
        taskAtIndex(index).assertTextEquals(text)
    }

    fun taskIsDone(taskName: String, isDone: Boolean) {
        val checkbox = SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Checkbox)
        composeTestRule.onNode(checkbox)
            .assert(hasParent(hasText(taskName)))
            .run {
                if (isDone) {
                    assertIsSelected()
                } else {
                    assertIsNotSelected()
                }
            }
    }

    fun toggleTask(taskName: String) {
        val checkbox = SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Checkbox)
        composeTestRule.onNode(checkbox)
            .assert(hasParent(hasText(taskName)))
            .performClick()
    }

    private fun taskAtIndex(index: Int): SemanticsNodeInteraction {
        return taskList.onChildAt(index)
    }

    // TODO: remove task

    // TODO: mark task as donde/undone via task action
}
