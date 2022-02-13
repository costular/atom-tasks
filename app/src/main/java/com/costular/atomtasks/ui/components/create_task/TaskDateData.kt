package com.costular.atomtasks.ui.components.create_task

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.costular.atomtasks.ui.components.DatePicker
import com.costular.atomtasks.ui.theme.AppTheme
import java.time.LocalDate

@Composable
internal fun TaskDateData(
    date: LocalDate,
    onSelectDate: (LocalDate) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(AppTheme.dimens.contentMargin)
    ) {
        DatePicker(
            onDateSelected = onSelectDate,
            currentDate = date,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
