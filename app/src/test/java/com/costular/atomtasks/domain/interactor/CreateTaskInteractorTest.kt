package com.costular.atomtasks.domain.interactor

import app.cash.turbine.test
import com.costular.atomtasks.domain.InvokeError
import com.costular.atomtasks.domain.InvokeSuccess
import com.costular.atomtasks.domain.manager.ReminderManager
import com.costular.atomtasks.domain.repository.TasksRepository
import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import java.time.LocalDate
import java.time.LocalTime
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
class CreateTaskInteractorTest {

    private lateinit var createTaskInteractor: CreateTaskInteractor

    private val tasksRepository: TasksRepository = mockk(relaxed = true)
    private val reminderManager: ReminderManager = mockk(relaxed = true)

    @Before
    fun setUp() {
        createTaskInteractor = CreateTaskInteractor(
            tasksRepository = tasksRepository,
            reminderManager = reminderManager
        )
    }

    @Test
    fun `should call repository with given input when create task`() = runBlockingTest {
        val name = "Call my mom"
        val date = LocalDate.of(2021, 1, 7)
        val reminder = LocalTime.of(9, 0)

        createTaskInteractor.executeSync(CreateTaskInteractor.Params(
            name = name,
            date = date,
            reminderEnabled = true,
            reminderTime = reminder
        ))

        coEvery { tasksRepository.createTask(name, date, true, reminder) }
    }

    @Test
    fun `should set reminder when create task given reminder's been passed correctly`() =
        runBlockingTest {
            val name = "Call my mom"
            val date = LocalDate.of(2021, 1, 7)
            val reminder = LocalTime.of(9, 0)
            val taskId = 100L

            coEvery {
                tasksRepository.createTask(name, date, true, reminder)
            } returns taskId

            createTaskInteractor.executeSync(
                CreateTaskInteractor.Params(
                    name = name,
                    date = date,
                    reminderEnabled = true,
                    reminderTime = reminder
                )
            )

            verify { reminderManager.set(taskId, reminder.atDate(date)) }
        }

}