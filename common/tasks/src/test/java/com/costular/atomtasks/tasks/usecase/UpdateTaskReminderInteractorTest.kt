package com.costular.atomtasks.tasks.usecase

import com.costular.atomtasks.tasks.repository.TasksRepository
import io.mockk.coVerify
import io.mockk.mockk
import java.time.LocalDate
import java.time.LocalTime
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class UpdateTaskReminderInteractorTest {

    lateinit var sut: UpdateTaskReminderInteractor

    private val tasksRepository: TasksRepository = mockk(relaxed = true)

    @Before
    fun setUp() {
        sut = UpdateTaskReminderInteractor(tasksRepository)
    }

    @Test
    fun `should call tasks repository accordingly when update task reminder`() = runTest {
        val taskId = 100L
        val reminder = LocalTime.of(11, 0)
        val newDate = LocalDate.now().plusDays(2)

        sut.invoke(UpdateTaskReminderInteractor.Params(taskId, reminder, newDate))

        coVerify { tasksRepository.updateTaskReminder(taskId, reminder, newDate) }
    }
}
