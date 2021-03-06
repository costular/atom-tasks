package com.costular.atomtasks.ui.components.createtask

import androidx.compose.ui.focus.FocusRequester
import com.costular.atomtasks.R
import com.costular.atomtasks.ui.base.AndroidTest
import com.costular.atomtasks.ui.base.getString
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.mockk
import io.mockk.verify
import java.time.LocalDate
import java.time.LocalTime
import org.junit.Test

@HiltAndroidTest
class CreateTaskExpandedTest : AndroidTest() {

    private val save: () -> Unit = mockk(relaxed = true)

    @Test
    fun shouldShowInputText_whenNameSetInState() {
        val taskName = "This is a test :)"

        givenCreateTaskExpanded(
            state = CreateTaskExpandedState(
                name = taskName,
            ),
        )

        createTaskExpanded {
            hasName(taskName)
        }
    }

    @Test
    fun shouldShowSave_whenNameIsSet() {
        val taskName = "This is a test :)"

        givenCreateTaskExpanded(
            state = CreateTaskExpandedState(
                name = taskName,
            ),
        )

        createTaskExpanded {
            canSave()
        }
    }

    @Test
    fun shouldShowToday_whenDateIsToday() {
        val date = LocalDate.now()

        givenCreateTaskExpanded(
            state = CreateTaskExpandedState(
                date = date,
            ),
        )

        createTaskExpanded {
            hasDate(composeTestRule.getString(R.string.day_today))
        }
    }

    @Test
    fun shouldShowReminder_whenReminderIsSet() {
        val reminder = LocalTime.of(13, 0)

        givenCreateTaskExpanded(
            state = CreateTaskExpandedState(
                reminder = reminder,
            ),
        )

        createTaskExpanded {
            hasReminder("13:00")
        }
    }

    @Test
    fun shouldCallSaveLambda_whenTypeNameAndSave() {
        givenCreateTaskExpanded(
            state = CreateTaskExpandedState(
                name = "whatever",
            ),
        )

        createTaskExpanded {
            save()
            verify { save() }
        }
    }

    private fun givenCreateTaskExpanded(
        state: CreateTaskExpandedState = CreateTaskExpandedState.Empty,
    ) {
        composeTestRule.setContent {
            CreateTaskExpanded(
                state = state,
                focusRequester = FocusRequester(),
                onValueChange = {},
                onClearReminder = {},
                onClickDate = {},
                onClickReminder = {},
                onSave = save,
            )
        }
    }
}
