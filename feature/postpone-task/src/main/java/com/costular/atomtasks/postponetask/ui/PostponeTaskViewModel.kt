package com.costular.atomtasks.postponetask.ui

import androidx.lifecycle.viewModelScope
import com.costular.atomtasks.core.ui.mvi.MviViewModel
import com.costular.atomtasks.postponetask.domain.GetPostponeChoiceListUseCase
import com.costular.atomtasks.postponetask.domain.PostponeChoice
import com.costular.atomtasks.tasks.interactor.PostponeTaskUseCase
import com.costular.atomtasks.notifications.TaskNotificationManager
import com.costular.atomtasks.postponetask.domain.PostponeChoiceCalculator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class PostponeTaskViewModel @Inject constructor(
    private val getPostponeChoiceListUseCase: GetPostponeChoiceListUseCase,
    private val postponeTaskUseCase: PostponeTaskUseCase,
    private val taskNotificationManager: TaskNotificationManager,
    private val postponeChoiceCalculator: PostponeChoiceCalculator,
) : MviViewModel<PostponeTaskScreenUiState>(PostponeTaskScreenUiState()) {

    fun initialize(taskId: Long) {
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

            // Remove notification as soon as the user decides to postpone the task, not before
            taskNotificationManager.removeTaskNotification(taskId)

            val reminder = postponeChoiceCalculator.calculatePostpone(postponeChoice)

            postponeTaskUseCase.invoke(
                PostponeTaskUseCase.Params(
                    taskId = taskId,
                    day = reminder.toLocalDate(),
                    time = reminder.toLocalTime(),
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
    }
}
