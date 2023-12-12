package com.costular.atomtasks.tasks.format

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.costular.atomtasks.tasks.model.RecurrenceType
import com.costular.atomtasks.core.ui.R.string as S

@Composable
fun RecurrenceType.localized(): String {
    val stringRes = when(this) {
        RecurrenceType.DAILY -> S.task_recurrence_daily
        RecurrenceType.WEEKDAYS -> S.task_recurrence_weekdays
        RecurrenceType.WEEKLY -> S.task_recurrence_weekly
        RecurrenceType.MONTHLY -> S.task_recurrence_monthly
        RecurrenceType.YEARLY -> S.task_recurrence_yearly
    }
    return stringResource(stringRes)
}
