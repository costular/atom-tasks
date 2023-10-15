package com.costular.atomtasks.tasks.createtask

import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.printToLog
import com.costular.atomtasks.core.testing.ui.ComposeProvider
import com.costular.atomtasks.core.testing.ui.getString
import io.mockk.verify
import java.time.LocalDate
import java.time.LocalTime
import org.junit.Test
import com.costular.atomtasks.core.ui.R
import org.junit.Ignore
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

    @Ignore("Check that lambda was called")
    @Test
    fun shouldCallSaveLambda_whenTypeNameAndSave() {
        givenCreateTaskExpanded(
            state = CreateTaskExpandedState(
                name = "whatever",
            ),
        )

        createTaskExpanded {
            save()
            TODO("Verify assert callback was called")
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
