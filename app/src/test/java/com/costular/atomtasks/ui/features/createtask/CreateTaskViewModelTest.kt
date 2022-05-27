package com.costular.atomtasks.ui.features.createtask

import app.cash.turbine.test
import com.costular.atomtasks.MviViewModelTest
import com.costular.atomtasks.domain.Async
import com.costular.atomtasks.domain.InvokeError
import com.costular.atomtasks.domain.InvokeStarted
import com.costular.atomtasks.domain.InvokeSuccess
import com.costular.atomtasks.domain.interactor.CreateTaskInteractor
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import java.time.LocalDate
import java.time.LocalTime
import kotlin.time.ExperimentalTime
import kotlinx.coroutines.flow.flow
import org.junit.Before
import org.junit.Test

@ExperimentalTime
class CreateTaskViewModelTest : MviViewModelTest() {

    private val createTaskInteractor: CreateTaskInteractor = mockk()

    lateinit var sut: CreateTaskViewModel

    @Before
    fun setUp() {
        sut = CreateTaskViewModel(createTaskInteractor)
    }

    @Test
    fun `should expose success when create task succeed`() = testBlocking {
        val name = "Task's name"
        val date = LocalDate.of(2022, 2, 10)
        val reminder = LocalTime.of(0, 0)
        coEvery {
            createTaskInteractor(
                CreateTaskInteractor.Params(
                    name,
                    date,
                    true,
                    reminder,
                ),
            )
        } returns flow {
            emit(InvokeStarted)
            emit(InvokeSuccess)
        }

        sut.createTask(name, date, reminder)

        sut.state.test {
            assertThat(expectMostRecentItem().savingTask).isInstanceOf(Async.Success::class.java)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `should expose failure when create task fails`() = testBlocking {
        val exception = Exception("some error")

        coEvery {
            createTaskInteractor(any())
        } returns flow {
            emit(InvokeError(exception))
        }

        sut.createTask("whatever", LocalDate.now(), LocalTime.now())

        sut.state.test {
            assertThat(expectMostRecentItem().savingTask).isEqualTo(Async.Failure(exception))
            cancelAndIgnoreRemainingEvents()
        }
    }
}
