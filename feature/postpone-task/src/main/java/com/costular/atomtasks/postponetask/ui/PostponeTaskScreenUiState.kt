package com.costular.atomtasks.postponetask.ui

import com.costular.atomtasks.postponetask.domain.PostponeChoice

data class PostponeTaskScreenUiState(
    val taskId: Long = -1L,
    val postponeChoices: PostponeChoiceListState = PostponeChoiceListState.Idle,
)

sealed interface PostponeChoiceListState {

    data object Idle : PostponeChoiceListState

    data object Loading : PostponeChoiceListState

    data class Success(
        val choices: List<PostponeChoice>,
    ) : PostponeChoiceListState
}
