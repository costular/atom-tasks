package com.costular.atomtasks.tasks.createtask

import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.test.junit4.createComposeRule
import com.costular.atomtasks.core.testing.ui.ComposeProvider
import com.costular.atomtasks.core.testing.ui.getString
import io.mockk.verify
import java.time.LocalDate
import java.time.LocalTime
import org.junit.Test
import com.costular.atomtasks.core.ui.R
import org.junit.Rule
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [33])
@LooperMode(LooperMode.Mode.PAUSED)
class CreateTaskExpandedTest : ComposeProvider {

    @get:Rule
    override val composeTestRule = createComposeRule()

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
                onSave = {},
            )
        }
    }
}
