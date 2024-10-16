package com.costular.atomtasks.postponetask.ui

import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.costular.atomtasks.analytics.AtomAnalytics
import com.costular.atomtasks.core.ui.mvi.MviViewModel
import com.costular.atomtasks.postponetask.domain.GetPostponeChoiceListUseCase
import com.costular.atomtasks.postponetask.domain.PostponeChoiceType
import com.costular.atomtasks.tasks.usecase.PostponeTaskUseCase
import com.costular.atomtasks.notifications.TaskNotificationManager
import com.costular.atomtasks.postponetask.domain.PostponeChoice
import com.costular.atomtasks.tasks.analytics.NotificationsActionsPostpone
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@HiltViewModel
class PostponeTaskViewModel @Inject constructor(
    private val getPostponeChoiceListUseCase: GetPostponeChoiceListUseCase,
    private val postponeTaskUseCase: PostponeTaskUseCase,
    private val taskNotificationManager: TaskNotificationManager,
    private val analytics: AtomAnalytics,
) : MviViewModel<PostponeTaskScreenUiState>(PostponeTaskScreenUiState()) {

    fun initialize(taskId: Long) {
        analytics.track(NotificationsActionsPostpone)
        setTaskId(taskId)
        loadPostponeChoices()
    }

    private fun setTaskId(taskId: Long) {
        setState {
            copy(taskId = taskId)
        }
    }

    private fun loadPostponeChoices() {
        viewModelScope.launch {
            setState {
                copy(postponeChoices = PostponeChoiceListState.Loading)
            }

            val result = getPostponeChoiceListUseCase(Unit)

            setState {
                copy(postponeChoices = PostponeChoiceListState.Success(choices = result))
            }
        }
    }

    fun onSelectPostponeChoice(postponeChoice: PostponeChoice) {
        viewModelScope.launch {
            val taskId = state.value.taskId

            if (postponeChoice.postponeChoiceType is PostponeChoiceType.Custom) {
                showCustomPostpone()
                return@launch
            }

            analytics.track(
                PostponeDefaultOptionClicked(postponeChoice.postponeChoiceType.toString())
            )

            rescheduleWithDateTime(
                taskId,
                postponeChoice.postponeDateTime!!.toLocalDate(),
                postponeChoice.postponeDateTime.toLocalTime()
            )
        }
    }

    private suspend fun rescheduleWithDateTime(
        taskId: Long,
        day: LocalDate,
        time: LocalTime,
    ) {
        taskNotificationManager.removeTaskNotification(taskId)

        postponeTaskUseCase.invoke(
            PostponeTaskUseCase.Params(
                taskId = taskId,
                day = day,
                time = time,
            )
        ).fold(
            ifError = {
                // For now we won't handle the error
            },
            ifResult = {
                sendEvent(PostponeTaskUiEvents.PostponedSuccessfully)
            }
        )
    }

    private fun showCustomPostpone() {
        analytics.track(PostponeCustomOptionClicked)
        val automaticPostpone = LocalDateTime.now().plusMinutes(15)

        setState {
            copy(
                showCustomPostponeChoice = true,
                customPostponeDate = automaticPostpone.toLocalDate(),
                customPostponeTime = automaticPostpone.toLocalTime(),
            )
        }
    }

    fun onClickCustomDate() {
        analytics.track(PostponeCustomDatePickerOpened)
        setState {
            copy(
                isSelectDayDialogOpen = true,
            )
        }
    }

    fun dismissCustomDate() {
        setState {
            copy(
                isSelectDayDialogOpen = false,
            )
        }
    }

    fun onUpdateDate(date: LocalDate) {
        setState {
            copy(
                isSelectDayDialogOpen = false,
                customPostponeDate = date
            )
        }
    }

    fun onClickCustomTime() {
        analytics.track(PostponeCustomTimePickerOpened)
        setState {
            copy(
                isSelectTimeDialogOpen = true,
            )
        }
    }

    fun dismissCustomTime() {
        setState {
            copy(
                isSelectTimeDialogOpen = false,
            )
        }
    }

    fun onUpdateTime(time: LocalTime) {
        setState {
            copy(
                isSelectTimeDialogOpen = false,
                customPostponeTime = time
            )
        }
    }

    fun customReschedule() {
        viewModelScope.launch {
            val date = requireNotNull(state.value.customPostponeDate)
            val time = requireNotNull(state.value.customPostponeTime)
            val taskId = state.value.taskId

            analytics.track(PostponeCustomRescheduled(date, time))

            rescheduleWithDateTime(
                taskId,
                date,
                time,
            )
        }
    }
}
