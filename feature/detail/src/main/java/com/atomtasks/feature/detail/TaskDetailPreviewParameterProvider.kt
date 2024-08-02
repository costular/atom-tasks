package com.atomtasks.feature.detail

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.costular.atomtasks.tasks.model.RecurrenceType
import java.time.LocalDate
import java.time.LocalTime

class TaskDetailPreviewParameterProvider : PreviewParameterProvider<TaskDetailUiState> {
    override val values: Sequence<TaskDetailUiState> = sequenceOf(
        TaskDetailUiState(),
        TaskDetailUiState(
            name = TextFieldState("\uD83C\uDFC3\u200D♂\uFE0F Go out for running"),
            reminder = LocalTime.now().plusHours(4),
            date = LocalDate.now().plusDays(4),
            recurrenceType = RecurrenceType.DAILY,
            isSaving = false,
        ),
        TaskDetailUiState(
            name = TextFieldState("\uD83C\uDFC3\u200D♂\uFE0F Go out for running"),
            reminder = LocalTime.now().plusHours(4),
            date = LocalDate.now().plusDays(4),
            recurrenceType = RecurrenceType.DAILY,
            isSaving = true,
        ),
    )
}

