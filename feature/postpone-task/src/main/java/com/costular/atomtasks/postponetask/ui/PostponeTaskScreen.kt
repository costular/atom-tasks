package com.costular.atomtasks.postponetask.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.costular.atomtasks.postponetask.domain.PostponeChoice
import com.costular.designsystem.components.CircularLoadingIndicator
import com.costular.designsystem.dialogs.AtomSheetTitle
import com.costular.designsystem.theme.AppTheme
import com.costular.designsystem.theme.AtomTheme
import com.costular.atomtasks.core.ui.R.string as S

@Composable
fun PostponeTaskScreen(
    taskId: Long,
    onClose: () -> Unit,
    viewModel: PostponeTaskViewModel = hiltViewModel(),
) {
    viewModel.initialize(taskId)
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel) {
        viewModel.uiEvents.collect { event ->
            when (event) {
                is PostponeTaskUiEvents.PostponedSuccessfully -> onClose()
            }
        }
    }

    PostponeTaskScreen(
        state = state,
        onPickPostponeChoice = viewModel::onSelectPostponeChoice,
        onClose = onClose,
    )
}

@Composable
fun PostponeTaskScreen(
    state: PostponeTaskScreenUiState,
    onClose: () -> Unit,
    onPickPostponeChoice: (PostponeChoice) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.32f))
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        onClose()
                    },
                )
            },
        contentAlignment = Alignment.BottomCenter,
    ) {
        val interactionSource = remember { MutableInteractionSource() }

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(AppTheme.dimens.contentMargin)
                .clickable(
                    indication = null,
                    interactionSource = interactionSource,
                ) {
                    // Do nothing, just to prevent parent pointerInput to be invoked
                    // when tapping anywhere on the Surface
                },
            shape = MaterialTheme.shapes.medium,
        ) {
            PostponeTaskScreenContent(
                state = state,
                onPickPostponeChoice = onPickPostponeChoice
            )
        }
    }
}

@Composable
private fun PostponeTaskScreenContent(
    state: PostponeTaskScreenUiState,
    onPickPostponeChoice: (PostponeChoice) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = AppTheme.dimens.contentMargin)
    ) {
        Spacer(Modifier.height(AppTheme.dimens.spacingMedium))

        AtomSheetTitle(
            text = stringResource(S.postpone_task_title),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = AppTheme.dimens.contentMargin),
        )

        Spacer(Modifier.height(AppTheme.dimens.spacingLarge))

        AnimatedContent(
            targetState = state.postponeChoices,
            label = "Choices content animation"
        ) {
            when (it) {
                is PostponeChoiceListState.Idle -> Unit
                is PostponeChoiceListState.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(AppTheme.dimens.contentMargin),
                        contentAlignment = Alignment.Center,
                    ) {
                        CircularLoadingIndicator()
                    }
                }

                is PostponeChoiceListState.Success -> {
                    PostponePicker(
                        modifier = Modifier.fillMaxWidth(),
                        actions = it.choices,
                        onAmountChoice = onPickPostponeChoice,
                    )
                }
            }
        }
    }
}

@Composable
private fun PostponePicker(
    actions: List<PostponeChoice>,
    onAmountChoice: (PostponeChoice) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(actions) { postponeChoice ->
            PostponePickerItem(
                title = postponeChoice.formatToText(),
                modifier = Modifier.fillMaxWidth(),
                onClick = { onAmountChoice(postponeChoice) })
        }
    }
}

@Composable
private fun PostponePickerItem(
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .clickable { onClick() }
            .padding(
                horizontal = AppTheme.dimens.contentMargin,
                vertical = AppTheme.dimens.spacingLarge
            ),
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Composable
internal fun PostponeChoice.formatToText(): String {
    return when (this) {
        PostponeChoice.FifteenMinutes -> stringResource(S.postpone_task_fifteen_minutes)
        PostponeChoice.OneHour -> stringResource(S.postpone_task_one_hour)
        PostponeChoice.Tonight -> stringResource(S.postpone_task_tonight)
        PostponeChoice.TomorrowMorning -> stringResource(S.postpone_task_tomorrow_morning)
        PostponeChoice.NextWeekend -> stringResource(S.postpone_task_next_weekend)
        PostponeChoice.NextWeek -> stringResource(S.postpone_task_next_week)
    }
}

@Preview
@Composable
fun PostponeTaskScreenPreview(
    @PreviewParameter(PostponeTaskScreenPreviewData::class) state: PostponeTaskScreenUiState,
) {
    AtomTheme {
        PostponeTaskScreen(
            state = state,
            onClose = {},
            onPickPostponeChoice = {},
        )
    }
}

