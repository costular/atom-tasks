package com.costular.atomtasks.ui.features.agenda

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.ComposeTestRule
import com.costular.atomtasks.ui.base.ComposeProvider
import com.costular.atomtasks.ui.base.Robot

fun ComposeProvider.agenda(func: AgendaRobot.() -> Unit) = AgendaRobot(composeTestRule).apply(func)

class AgendaRobot(composeTestRule: ComposeTestRule) : Robot(composeTestRule) {

    init {
        composeTestRule.onNodeWithTag("AgendaTitle")
            .assertIsDisplayed()
    }

    fun assertDayText(text: String) {
        composeTestRule.onNodeWithTag("AgendaTitle")
            .assertTextEquals(text)
    }

    fun goNextDay() {
        composeTestRule.onNodeWithTag("AgendaNextDay")
            .assertIsDisplayed()
            .performClick()
    }

    fun goPrevDay() {
        composeTestRule.onNodeWithTag("AgendaPrevDay")
            .assertIsDisplayed()
            .performClick()
    }

    fun goToday() {
        composeTestRule.onNodeWithTag("AgendaTitle")
            .performClick()
    }

    infix fun openCreateTask(func: CreateTaskRobot.() -> Unit): CreateTaskRobot {
        composeTestRule.onNodeWithTag("AgendaCreateTask")
            .assertIsDisplayed()
            .performClick()

        return CreateTaskRobot(composeTestRule).apply(func)
    }

    fun firstTaskHasText(text: String) {
        composeTestRule.onNodeWithTag("AgendaTaskList")
            .onChildAt(0)
            .assertTextEquals(text)
    }

    // TODO: mark task as done/undone

    // TODO: remove task

    // TODO: mark task as donde/undone via task action

}