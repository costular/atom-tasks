package com.costular.atomtasks.postponetask.ui

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.costular.atomtasks.postponetask.domain.DefaultPostponeChoiceCalculator
import com.costular.atomtasks.postponetask.domain.GetPostponeChoiceListUseCase
import com.costular.atomtasks.postponetask.domain.PostponeChoice
import java.time.Clock

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
                choices = GetPostponeChoiceListUseCase.Choices.map {
                    PostponeChoice(
                        it,
                        DefaultPostponeChoiceCalculator(Clock.systemDefaultZone()).calculatePostpone(
                            it
                        )
                    )
                }
            ),
        ),
        PostponeTaskScreenUiState(
            showCustomPostponeChoice = true,
            postponeChoices = PostponeChoiceListState.Success(
                choices = GetPostponeChoiceListUseCase.Choices.map {
                    PostponeChoice(
                        it,
                        DefaultPostponeChoiceCalculator(Clock.systemDefaultZone()).calculatePostpone(
                            it
                        )
                    )
                }
            ),
        )
    )
}
