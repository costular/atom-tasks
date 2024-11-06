package com.atomtasks.feature.detail

import androidx.compose.foundation.text.input.TextFieldState
import com.costular.atomtasks.tasks.model.RecurrenceType
import com.costular.atomtasks.tasks.removal.RemoveTaskConfirmationUiState
import java.time.LocalDate
import java.time.LocalTime

data class TaskDetailUiState(
    val initialTaskState: TaskState = TaskState(),
    val taskState: TaskState = TaskState(),
    val isEditMode: Boolean = false,
    val showSetDate: Boolean = false,
    val showSetReminder: Boolean = false,
    val showSetRecurrence: Boolean = false,
    val shouldShowExactAlarmRationale: Boolean = false,
    val isSaving: Boolean = false,
    val taskToSave: TaskToSave? = null,
    val isDone: Boolean = false,
    val removeTaskConfirmationUiState: RemoveTaskConfirmationUiState = RemoveTaskConfirmationUiState.Hidden,
    val shouldShowDiscardChangesConfirmation: Boolean = false,
) {
    val taskHasBeenEdited: Boolean
        get() = initialTaskState != taskState
}

data class TaskState(
    val name: TextFieldState = TextFieldState(),
    val date: LocalDate = LocalDate.now(),
    val reminder: LocalTime? = null,
    val recurrenceType: RecurrenceType? = null,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is TaskState) return false

        return name.text == other.name.text &&
                date == other.date &&
                reminder == other.reminder &&
                recurrenceType == other.recurrenceType
    }

    override fun hashCode(): Int {
        return name.text.hashCode() * 31 +
                date.hashCode() * 31 +
                (reminder?.hashCode() ?: 0) * 31 +
                (recurrenceType?.hashCode() ?: 0)
    }
}

data class TaskToSave(
    val name: String,
    val date: LocalDate,
    val reminder: LocalTime?,
    val recurrenceType: RecurrenceType?,
)
