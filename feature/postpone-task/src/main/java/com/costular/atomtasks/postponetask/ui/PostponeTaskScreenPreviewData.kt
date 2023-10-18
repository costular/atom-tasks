package com.costular.atomtasks.postponetask.ui

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.costular.atomtasks.postponetask.domain.PostponeChoice

internal class PostponeTaskScreenPreviewData : PreviewParameterProvider<PostponeTaskScreenUiState> {
    override val values: Sequence<PostponeTaskScreenUiState> = sequenceOf(
        PostponeTaskScreenUiState(
            postponeChoices = PostponeChoiceListState.Idle,
        ),
        PostponeTaskScreenUiState(
            postponeChoices = PostponeChoiceListState.Loading,
        ),
        PostponeTaskScreenUiState(
            postponeChoices = PostponeChoiceListState.Success(
                choices = listOf(
                    PostponeChoice.FifteenMinutes,
                    PostponeChoice.OneHour,
                    PostponeChoice.Tonight,
                )
            ),
        )
    )
}
