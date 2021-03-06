package com.costular.atomtasks.ui.dialogs.timepicker

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.costular.atomtasks.R
import com.costular.atomtasks.ui.components.TimePicker
import com.costular.atomtasks.ui.theme.AppTheme
import com.costular.atomtasks.ui.theme.AtomRemindersTheme
import com.costular.atomtasks.ui.util.rememberFlowWithLifecycle
import java.time.LocalTime
import kotlinx.coroutines.flow.collect

@Composable
fun TimePickerDialog(
    modifier: Modifier = Modifier,
    time: LocalTime = LocalTime.now(),
    timeSuggestions: List<LocalTime> = emptyList(),
    onTimeChange: (LocalTime) -> Unit,
    onCancel: () -> Unit,
    paddingValues: PaddingValues = PaddingValues(AppTheme.dimens.contentMargin),
    viewModel: TimePickerDialogViewModel = hiltViewModel(),
) {
    val state by rememberFlowWithLifecycle(viewModel.state)
        .collectAsState(TimePickerDialogState.Empty)

    LaunchedEffect(time) {
        viewModel.setTime(time)
    }

    LaunchedEffect(viewModel) {
        viewModel.uiEvents.collect { event ->
            when (event) {
                is TimePickerUiEvents.Save -> {
                    onTimeChange(event.time)
                }
                is TimePickerUiEvents.Cancel -> {
                    onCancel()
                }
            }
        }
    }

    Dialog(onDismissRequest = onCancel) {
        Surface(
            color = MaterialTheme.colors.background,
            shape = MaterialTheme.shapes.medium,
        ) {
            Column {
                Text(
                    text = stringResource(R.string.create_task_set_reminder),
                    style = MaterialTheme.typography.h5,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(horizontal = AppTheme.dimens.contentMargin)
                        .padding(vertical = AppTheme.dimens.spacingXLarge),
                )

                TimePicker(
                    modifier = modifier,
                    time = state.time,
                    timeSuggestions = timeSuggestions,
                    onTimeChange = viewModel::setTime,
                    paddingValues = paddingValues,
                )

                Row(
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(AppTheme.dimens.spacingMedium),
                ) {
                    TextButton(onClick = viewModel::cancel) {
                        Text(stringResource(R.string.cancel))
                    }

                    TextButton(onClick = viewModel::save) {
                        Text(stringResource(R.string.save))
                    }
                }
            }
        }
    }
}

@Suppress("MagicNumber")
@Preview
@Composable
fun TimePickerDialogPreview() {
    AtomRemindersTheme {
        TimePickerDialog(
            time = LocalTime.of(9, 0),
            timeSuggestions = listOf(
                LocalTime.of(9, 0),
                LocalTime.of(11, 0),
                LocalTime.of(18, 0),
            ),
            onTimeChange = {},
            onCancel = {},
        )
    }
}
