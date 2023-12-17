package com.costular.atomtasks.tasks

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.costular.atomtasks.core.testing.ui.AndroidTest
import com.costular.atomtasks.tasks.model.TaskCard
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

@HiltAndroidTest
class TaskCardTest : AndroidTest() {

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
                isBeingDragged = false,
                recurrenceType = null,
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
                isBeingDragged = false,
                recurrenceType = null,
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
