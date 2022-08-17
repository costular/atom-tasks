package com.costular.common_ui.components

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.costular.atomtasks.core_testing.ui.AndroidTest
import com.costular.atomtasks.data.tasks.Reminder
import com.costular.commonui.components.TaskCard
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.mockk
import io.mockk.verify
import java.time.LocalDate
import java.time.LocalTime
import org.junit.Test

@HiltAndroidTest
class TaskCardTest : AndroidTest() {

    @Test
    fun shouldShowText() {
        val text = "this is a test :)"

        composeTestRule.setContent {
            TaskCard(
                title = text,
                isFinished = false,
                reminder = null,
                onMark = {},
                onOpen = {},
            )
        }

        composeTestRule.onNodeWithText(text)
            .assertIsDisplayed()
    }

    @Test
    fun shouldShowReminder_whenReminderIsSet() {
        val text = "whatever"
        val reminderTime = LocalTime.of(9, 0)
        val reminder = Reminder(
            1L,
            reminderTime,
            true,
            LocalDate.now(),
        )
        val reminderText = reminderTime.toString()

        composeTestRule.setContent {
            TaskCard(
                title = text,
                isFinished = false,
                reminder = reminder,
                onMark = {},
                onOpen = {},
            )
        }

        composeTestRule.onNodeWithText("[alarm] $reminderText")
            .assertIsDisplayed()
    }

    @Test
    fun shouldCallMarkCallback_whenTapOnMarkable() {
        val onMarkCallback: () -> Unit = mockk(relaxed = true)

        composeTestRule.setContent {
            TaskCard(
                title = "whatever",
                isFinished = false,
                reminder = null,
                onMark = onMarkCallback,
                onOpen = {},
            )
        }

        composeTestRule.onNodeWithTag("Markable")
            .performClick()

        verify {
            onMarkCallback()
        }
    }

    @Test
    fun shouldCallClickCallback_whenTapOnAnywhere() {
        val text = "whatever"
        val onClickCallback: () -> Unit = mockk(relaxed = true)

        composeTestRule.setContent {
            TaskCard(
                title = text,
                isFinished = true,
                reminder = null,
                onMark = {},
                onOpen = onClickCallback,
            )
        }

        composeTestRule.onNodeWithText(text)
            .assertIsDisplayed()
            .performClick()

        verify {
            onClickCallback()
        }
    }
}
