package com.costular.atomtasks.postponetask.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.costular.atomtasks.core.ui.utils.DateUtils.dayAsText
import com.costular.atomtasks.core.ui.utils.ofLocalizedTime
import com.costular.atomtasks.core.util.DateTimeFormatters.dateWithMonthFormatter
import com.costular.atomtasks.postponetask.domain.PostponeChoice
import com.costular.atomtasks.postponetask.domain.PostponeChoiceType
import com.costular.designsystem.components.CircularLoadingIndicator
import com.costular.designsystem.components.PrimaryButton
import com.costular.designsystem.dialogs.AtomSheetTitle
import com.costular.designsystem.dialogs.DatePickerDialog
import com.costular.designsystem.dialogs.TimePickerDialog
import com.costular.designsystem.theme.AppTheme
import com.costular.designsystem.theme.AtomTheme
import java.time.LocalDate
import java.time.LocalDateTime
import com.costular.atomtasks.core.ui.R.string as S

@OptIn(ExperimentalMaterial3Api::class)
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

    if (state.isSelectDayDialogOpen) {
        DatePickerDialog(
            onDismiss = viewModel::dismissCustomDate,
            currentDate = requireNotNull(state.customPostponeDate),
            onDateSelected = viewModel::onUpdateDate,
        )
    }

    if (state.isSelectTimeDialogOpen) {
        TimePickerDialog(
            onDismiss = viewModel::dismissCustomTime,
            selectedTime = requireNotNull(state.customPostponeTime),
            onSelectTime = viewModel::onUpdateTime
        )
    }

    PostponeTaskScreen(
        state = state,
        onPickPostponeChoice = viewModel::onSelectPostponeChoice,
        onClickCustomDate = viewModel::onClickCustomDate,
        onClickCustomTime = viewModel::onClickCustomTime,
        customReschedule = viewModel::customReschedule,
        onClose = onClose,
    )
}

@Composable
fun PostponeTaskScreen(
    state: PostponeTaskScreenUiState,
    onClose: () -> Unit,
    onClickCustomDate: () -> Unit,
    onClickCustomTime: () -> Unit,
    customReschedule: () -> Unit,
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
                onClickCustomDate = onClickCustomDate,
                onClickCustomTime = onClickCustomTime,
                onPickPostponeChoice = onPickPostponeChoice,
                customReschedule = customReschedule,
            )
        }
    }
}

@Composable
private fun PostponeTaskScreenContent(
    state: PostponeTaskScreenUiState,
    onClickCustomDate: () -> Unit,
    onClickCustomTime: () -> Unit,
    customReschedule: () -> Unit,
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

        when (state.postponeChoices) {
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
                    actions = state.postponeChoices.choices,
                    onAmountChoice = onPickPostponeChoice,
                )
            }
        }

        CustomPostponeChoice(
            state = state,
            onClickCustomDate = onClickCustomDate,
            onClickCustomTime = onClickCustomTime,
            customReschedule = customReschedule
        )
    }
}

@Composable
private fun ColumnScope.CustomPostponeChoice(
    state: PostponeTaskScreenUiState,
    onClickCustomDate: () -> Unit,
    onClickCustomTime: () -> Unit,
    customReschedule: () -> Unit
) {
    AnimatedVisibility(state.showCustomPostponeChoice) {
        Column {
            DateTimePicker(
                label = stringResource(S.postpone_task_custom_date),
                content = state.customPostponeDate?.let { dayAsText(state.customPostponeDate) }
                    ?: "--",
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.CalendarToday,
                        contentDescription = null,
                    )
                },
                onClick = onClickCustomDate,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = AppTheme.dimens.contentMargin)
            )

            Spacer(Modifier.height(AppTheme.dimens.spacingLarge))

            DateTimePicker(
                label = stringResource(S.postpone_task_custom_time),
                content = state.customPostponeTime?.ofLocalizedTime() ?: "--",
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.AccessTime,
                        contentDescription = null,
                    )
                },
                onClick = onClickCustomTime,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = AppTheme.dimens.contentMargin)
            )

            Spacer(modifier = Modifier.height(AppTheme.dimens.spacingLarge))

            PrimaryButton(
                onClick = customReschedule,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = AppTheme.dimens.contentMargin)
            ) {
                Text("Reschedule")
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
        itemsIndexed(actions) { index, postponeChoice ->
            if (index == actions.lastIndex) {
                HorizontalDivider()
            }

            PostponePickerItem(
                title = postponeChoice.postponeChoiceType.formatToText(),
                description = postponeChoice.postponeDateTime?.formatToText(),
                modifier = Modifier.fillMaxWidth(),
                onClick = { onAmountChoice(postponeChoice) })
        }
    }
}

@Composable
private fun PostponePickerItem(
    title: String,
    description: String?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .clickable { onClick() }
            .padding(
                horizontal = AppTheme.dimens.contentMargin,
                vertical = AppTheme.dimens.spacingLarge
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )

        if (description != null) {
            Box(
                modifier = Modifier
                    .padding(horizontal = AppTheme.dimens.spacingMedium)
                    .size(4.dp)
                    .background(MaterialTheme.colorScheme.onSurface, CircleShape)
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary,
                maxLines = 1,
            )
        }
    }
}

@Composable
private fun DateTimePicker(
    label: String,
    content: String,
    leadingIcon: @Composable () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TextField(
        value = content,
        onValueChange = {},
        readOnly = true,
        label = {
            Text(label)
        },
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = null,
            )
        },
        leadingIcon = leadingIcon,
        modifier = modifier
            .pointerInput(Unit) {
                awaitEachGesture {
                    awaitFirstDown(pass = PointerEventPass.Initial)
                    val upEvent = waitForUpOrCancellation(pass = PointerEventPass.Initial)
                    if (upEvent != null) {
                        onClick()
                    }
                }
            },
    )
}

@Composable
internal fun PostponeChoiceType.formatToText(): String {
    return when (this) {
        PostponeChoiceType.ThirtyMinutes -> stringResource(S.postpone_task_thirty_minutes)
        PostponeChoiceType.OneHour -> stringResource(S.postpone_task_one_hour)
        PostponeChoiceType.ThreeHours -> stringResource(S.postpone_task_three_hours)
        PostponeChoiceType.Tonight -> stringResource(S.postpone_task_tonight)
        PostponeChoiceType.TomorrowMorning -> stringResource(S.postpone_task_tomorrow_morning)
        PostponeChoiceType.NextWeekend -> stringResource(S.postpone_task_next_weekend)
        PostponeChoiceType.NextWeek -> stringResource(S.postpone_task_next_week)
        PostponeChoiceType.Custom -> stringResource(S.postpone_task_custom)
    }
}

@Composable
internal fun LocalDateTime.formatToText(): String {
    val time = toLocalTime()
    return if (this.toLocalDate() != LocalDate.now()) {
        "${dateWithMonthFormatter.format(this)} - ${time.ofLocalizedTime()}"
    } else {
        time.ofLocalizedTime()
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
            onClickCustomDate = {},
            onClickCustomTime = {},
            customReschedule = {},
        )
    }
}
