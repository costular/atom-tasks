package com.costular.atomtasks.domain.interactor

import com.costular.atomtasks.domain.repository.TasksRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import java.time.LocalTime

@ExperimentalCoroutinesApi
class UpdateTaskReminderInteractorTest {

    private val coroutineTest = TestCoroutineDispatcher()

    lateinit var sut: UpdateTaskReminderInteractor

    private val tasksRepository: TasksRepository = mockk(relaxed = true)

    @Before
    fun setUp() {
        sut = UpdateTaskReminderInteractor(tasksRepository)
    }

    @Test
    fun `should call tasks repository accordingly when update task reminder`() =
        coroutineTest.runBlockingTest {
            val taskId = 100L
            val reminder = LocalTime.of(11, 0)

            sut.executeSync(UpdateTaskReminderInteractor.Params(taskId, reminder))

            coVerify { tasksRepository.updateTaskReminder(taskId, reminder) }
        }

}