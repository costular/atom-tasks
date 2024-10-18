package com.costular.atomtasks.postponetask.ui

import com.costular.atomtasks.postponetask.domain.PostponeChoice
import java.time.LocalDate
import java.time.LocalTime

data class PostponeTaskScreenUiState(
    val taskId: Long = -1L,
    val postponeChoices: PostponeChoiceListState = PostponeChoiceListState.Idle,
    val showCustomPostponeChoice: Boolean = false,
    val isSelectDayDialogOpen: Boolean = false,
    val isSelectTimeDialogOpen: Boolean = false,
    val customPostponeDate: LocalDate? = null,
    val customPostponeTime: LocalTime? = null,
)

sealed interface PostponeChoiceListState {

    data object Idle : PostponeChoiceListState

    data object Loading : PostponeChoiceListState

    data class Success(
        val choices: List<PostponeChoice>,
    ) : PostponeChoiceListState
}
