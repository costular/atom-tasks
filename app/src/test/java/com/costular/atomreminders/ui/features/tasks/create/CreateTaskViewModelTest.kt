package com.costular.atomreminders.ui.features.tasks.create

import app.cash.turbine.test
import com.costular.atomreminders.MviViewModelTest
import com.costular.atomreminders.domain.InvokeError
import com.costular.atomreminders.domain.InvokeStarted
import com.costular.atomreminders.domain.InvokeSuccess
import com.costular.atomreminders.domain.error.AtomError
import com.costular.atomreminders.domain.interactor.CreateTaskInteractor
import com.costular.atomreminders.ui.common.validation.FieldValidatorDefault
import com.costular.atomreminders.ui.features.tasks.create.CreateTaskUiEvent.NavigateUp
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.asFlow
import org.junit.Before
import org.junit.Test
import java.time.LocalDate
import java.time.LocalTime
import kotlin.time.ExperimentalTime

@ExperimentalTime
class CreateTaskViewModelTest : MviViewModelTest() {

    private val createTaskInteractor: CreateTaskInteractor = mockk()

    private lateinit var sut: CreateTaskViewModel

    @Before
    fun setUp() {
        sut = CreateTaskViewModel(
            createTaskInteractor,
            FieldValidatorDefault()
        )
    }

    @Test
    fun `Should update state's name when type name`() = testBlocking {
        val name = "Buy fruits! 🍌"

        sut.setName(name)

        sut.state.test {
            assertThat(awaitItem().name.value).isEqualTo(name)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `Should update state's date when set date`() = testBlocking {
        val date = LocalDate.of(2021, 11, 28)

        sut.setDate(date)

        sut.state.test {
            assertThat(awaitItem().date).isEqualTo(date)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `Should update state's reminder when set reminder`() = testBlocking {
        val reminder = LocalTime.parse("11:00")

        sut.setReminder(reminder)

        sut.state.test {
            assertThat(awaitItem().reminder).isEqualTo(reminder)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `Should be able to press save given date is future and name not empty`() = testBlocking {
        val date = LocalDate.now()
        val name = "Whatever 🎮"

        sut.setDate(date)
        sut.setName(name)

        sut.state.test {
            assertThat(awaitItem().canSave).isTrue()
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `Should not be able to press save given date is past`() = testBlocking {
        val date = LocalDate.now().minusDays(1)

        sut.setDate(date)
        sut.setName("whatever")

        sut.state.test {
            assertThat(awaitItem().canSave).isFalse()
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `Should not be able to press save given name is empty`() = testBlocking {
        val date = LocalDate.now()

        sut.setDate(date)

        sut.state.test {
            val lastState = awaitItem()
            assertThat(lastState.canSave).isFalse()
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `Should save when press save given valid inputs`() = testBlocking {
        val name = "Whatever"
        val date = LocalDate.now()
        val reminder = LocalTime.now()

        val result = listOf(
            InvokeStarted,
            InvokeSuccess
        )
        every {
            createTaskInteractor(CreateTaskInteractor.Params(
                name,
                date,
                true,
                reminder,
            ))
        } returns result.asFlow()

        sut.setName(name)
        sut.setDate(date)
        sut.setReminder(reminder)
        sut.save()

        sut.uiEvents.test {
            assertThat(awaitItem()).isInstanceOf(NavigateUp::class.java)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `Should show error when saving fails`() = testBlocking {
        val name = "Whatever"
        val date = LocalDate.now()
        val reminder = LocalTime.now()
        val exception = Exception()
        val error = AtomError.Unknown

        val result = listOf(
            InvokeStarted,
            InvokeError(exception)
        )
        every {
            createTaskInteractor(CreateTaskInteractor.Params(
                name,
                date,
                true,
                reminder,
            ))
        } returns result.asFlow()

        sut.setName(name)
        sut.setDate(date)
        sut.setReminder(reminder)
        sut.save()

        sut.uiEvents.test {
            assertThat(awaitItem()).isEqualTo(CreateTaskUiEvent.ShowError(error))
            cancelAndIgnoreRemainingEvents()
        }
    }

}