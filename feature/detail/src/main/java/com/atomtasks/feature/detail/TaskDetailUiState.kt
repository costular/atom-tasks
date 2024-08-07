package com.atomtasks.feature.detail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text.input.TextFieldState
import com.costular.atomtasks.tasks.model.RecurrenceType
import java.time.LocalDate
import java.time.LocalTime

@OptIn(ExperimentalFoundationApi::class)
data class TaskDetailUiState(
    val taskId: Long? = null,
    val name: TextFieldState = TextFieldState(),
    val date: LocalDate = LocalDate.now(),
    val reminder: LocalTime? = null,
    val showSetDate: Boolean = false,
    val showSetReminder: Boolean = false,
    val showSetRecurrence: Boolean = false,
    val shouldShowExactAlarmRationale: Boolean = false,
    val recurrenceType: RecurrenceType? = null,
    val isSaving: Boolean = false,
    val taskToSave: TaskToSave? = null,
) {
    val isEditMode: Boolean
        get() = taskId != null
}

data class TaskToSave(
    val name: String,
    val date: LocalDate,
    val reminder: LocalTime?,
    val recurrenceType: RecurrenceType?,
)
