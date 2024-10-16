package com.costular.atomtasks.postponetask.ui

import com.costular.atomtasks.analytics.AtomAnalytics
import com.costular.atomtasks.core.testing.MviViewModelTest
import com.costular.atomtasks.core.toResult
import com.costular.atomtasks.notifications.TaskNotificationManager
import com.costular.atomtasks.postponetask.domain.GetPostponeChoiceListUseCase
import com.costular.atomtasks.postponetask.domain.PostponeChoice
import com.costular.atomtasks.postponetask.domain.PostponeChoiceType
import com.costular.atomtasks.tasks.analytics.NotificationsActionsPostpone
import com.costular.atomtasks.tasks.usecase.PostponeTaskUseCase
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class PostponeTaskViewModelTest : MviViewModelTest() {

    private lateinit var sut: PostponeTaskViewModel

    private val getPostponeChoiceListUseCase: GetPostponeChoiceListUseCase =
        mockk(relaxUnitFun = true)
    private val postponeTaskUseCase: PostponeTaskUseCase = mockk(relaxUnitFun = true)
    private val taskNotificationManager: TaskNotificationManager = mockk(relaxUnitFun = true)
    private val analytics: AtomAnalytics = mockk(relaxed = true)

    @Before
    fun setup() {
        givenPostponeChoicesSucceeds()
        givenPostponeSucceeds()
        initializeViewModel()
    }

    @Test
    fun `Should expose postpone choices when usecase return successfully`() = runTest {
        sut.initialize(ANY_TASK_ID)

        assertThat(sut.state.value.postponeChoices).isEqualTo(
            PostponeChoiceListState.Success(
                FakeChoices
            )
        )

        coVerify(exactly = 1) { getPostponeChoiceListUseCase(Unit) }
    }

    @Test
    fun `Should expose loading when retreiving the postpone choice list`() = runTest {
        coEvery { getPostponeChoiceListUseCase(Unit) } coAnswers {
            delay(1000) // Simulate a loading delay
            FakeChoices
        }

        sut.initialize(ANY_TASK_ID)

        assertThat(sut.state.value.postponeChoices).isEqualTo(PostponeChoiceListState.Loading)

        advanceUntilIdle()

        assertThat(sut.state.value.postponeChoices).isEqualTo(
            PostponeChoiceListState.Success(FakeChoices)
        )
    }

    @Test
    fun `Should cancel the notification manager when the task is postponed`() = runTest {
        sut.initialize(ANY_TASK_ID)
        sut.onSelectPostponeChoice(FakeChoices.first())

        coVerify(exactly = 1) { taskNotificationManager.removeTaskNotification(ANY_TASK_ID) }
    }

    private fun givenPostponeSucceeds() {
        coEvery { postponeTaskUseCase.invoke(any()) } returns Unit.toResult()
    }

    private fun givenPostponeChoicesSucceeds() {
        coEvery { getPostponeChoiceListUseCase(Unit) } returns FakeChoices
    }

    @Test
    fun `Should show custom section when tap on custom postpone choice`() = runTest {
        sut.initialize(ANY_TASK_ID)
        val customChoice = FakeChoices.find { it.postponeChoiceType == PostponeChoiceType.Custom }!!
        sut.onSelectPostponeChoice(customChoice)

        assertThat(sut.state.value.showCustomPostponeChoice).isTrue()
    }

    @Test
    fun `Should track notifications actions postpone when initialize options`() = runTest {
        sut.initialize(ANY_TASK_ID)

        coEvery { analytics.track(NotificationsActionsPostpone) }
    }

    @Test
    fun `Should track custom postpone choice when tap on custom postpone choice`() = runTest {
        sut.initialize(ANY_TASK_ID)

        sut.onSelectPostponeChoice(CustomChoice)

        coEvery { analytics.track(PostponeCustomOptionClicked) }
    }

    @Test
    fun `Should track default postpone choice when tap on default postpone choice`() = runTest {
        sut.initialize(ANY_TASK_ID)
        val choice = FakeChoices.first()
        sut.onSelectPostponeChoice(choice)

        coEvery { analytics.track(PostponeDefaultOptionClicked("OneHour")) }
    }

    @Test
    fun `Should track custom date when tap on custom date`() = runTest {
        sut.initialize(ANY_TASK_ID)
        sut.onClickCustomDate()

        coEvery { analytics.track(PostponeCustomDatePickerOpened) }
    }

    @Test
    fun `Should track custom time when tap on custom time`() = runTest {
        sut.initialize(ANY_TASK_ID)
        sut.onClickCustomTime()

        coEvery { analytics.track(PostponeCustomTimePickerOpened) }
    }

    @Test
    fun `Should call schedule when tap on custom reschedule given date & time were selected`() =
        runTest {
            val date = LocalDate.now()
            val time = LocalTime.now()

            with(sut) {
                initialize(ANY_TASK_ID)
                onSelectPostponeChoice(CustomChoice)
                onUpdateDate(date)
                onUpdateTime(time)
            }

            coEvery {
                postponeTaskUseCase.invoke(
                    PostponeTaskUseCase.Params(
                        ANY_TASK_ID,
                        date,
                        time,
                    )
                )
            }
        }

    @Test
    fun `Should track custom reschedule when tap on custom reschedule given date & time were selected`() =
        runTest {
            val date = LocalDate.now()
            val time = LocalTime.now()

            with(sut) {
                initialize(ANY_TASK_ID)
                onSelectPostponeChoice(CustomChoice)
                onUpdateDate(date)
                onUpdateTime(time)
            }

            coEvery { analytics.track(PostponeCustomRescheduled(date, time)) }
        }

    private fun initializeViewModel() {
        sut = PostponeTaskViewModel(
            getPostponeChoiceListUseCase = getPostponeChoiceListUseCase,
            postponeTaskUseCase = postponeTaskUseCase,
            taskNotificationManager = taskNotificationManager,
            analytics = analytics,
        )
    }

    private companion object {
        const val ANY_TASK_ID = 123L
        val FakeChoices = listOf(
            PostponeChoice(PostponeChoiceType.OneHour, LocalDateTime.now().plusMinutes(15)),
            PostponeChoice(PostponeChoiceType.Tonight, LocalDate.now().atTime(20, 0)),
            PostponeChoice(PostponeChoiceType.Custom, null),
        )
        val CustomChoice = FakeChoices.find { it.postponeChoiceType == PostponeChoiceType.Custom }!!
    }
}
