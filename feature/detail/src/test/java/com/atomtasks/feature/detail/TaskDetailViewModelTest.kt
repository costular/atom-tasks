package com.atomtasks.feature.detail

import androidx.lifecycle.SavedStateHandle
import com.costular.atomtasks.analytics.AtomAnalytics
import com.costular.atomtasks.tasks.createtask.CreateTaskResult
import com.costular.atomtasks.tasks.model.RecurrenceType
import com.costular.atomtasks.tasks.usecase.AreExactRemindersAvailable
import com.costular.atomtasks.tasks.usecase.CreateTaskUseCase
import com.costular.atomtasks.tasks.usecase.EditTaskUseCase
import com.costular.atomtasks.tasks.usecase.GetTaskByIdUseCase
import com.google.common.truth.Truth.assertThat
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.time.LocalDate
import java.time.LocalTime

class TaskDetailViewModelTest {

    lateinit var sut: TaskDetailViewModel

    private val areExactRemindersAvailable: AreExactRemindersAvailable = mockk()
    private val getTaskByIdUseCase: GetTaskByIdUseCase = mockk()
    private val editTaskUseCase: EditTaskUseCase = mockk()
    private val createTaskUseCase: CreateTaskUseCase = mockk()
    private val atomAnalytics: AtomAnalytics = mockk()
    private val savedStateHandle: SavedStateHandle = mockk()

    @Before
    fun setUp() {
        sut = TaskDetailViewModel(
            savedStateHandle = savedStateHandle,
            areExactRemindersAvailable = areExactRemindersAvailable,
            getTaskByIdUseCase = getTaskByIdUseCase,
            editTaskUseCase = editTaskUseCase,
            createTaskUseCase = createTaskUseCase,
            atomAnalytics = atomAnalytics,
        )
    }

    @Test
    fun `should expose date when pass date`() = runTest {
        val date = LocalDate.of(2021, 12, 24)

        sut.onDateChanged(date)

        assertThat(sut.state.value.date).isEqualTo(date)
    }

    @Test
    fun `should expose reminder when pass local time`() = runTest {
        val localTime = LocalTime.of(9, 0)

        sut.onReminderChanged(localTime)

        assertThat(sut.state.value.reminder).isEqualTo(localTime)
    }

    @Test
    fun `should send save event with according data when request save`() = runTest {
        val name = "name"
        val date = LocalDate.of(2021, 12, 24)
        val reminder = LocalTime.of(9, 0)
        val recurrence = RecurrenceType.WEEKLY
        val expected = CreateTaskResult(name, date, reminder, recurrence)

//        sut.setName(name)
//        sut.setDate(date)
//        sut.setReminder(reminder)
//        sut.setRecurrence(RecurrenceType.WEEKLY)
//        sut.requestSave()
//
//        sut.uiEvents.test {
//            assertThat(expectMostRecentItem()).isEqualTo(CreateTaskUiEvents.SaveTask(expected))
//            cancelAndIgnoreRemainingEvents()
//        }
    }
}