package com.costular.atomreminders.ui.components.create_task

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.costular.atomreminders.ui.components.DatePicker
import com.costular.atomreminders.ui.theme.AppTheme
import java.time.LocalDate

@Composable
internal fun TaskDateData(
    date: LocalDate,
    onSelectDate: (LocalDate) -> Unit,
) {
    Column(modifier = Modifier
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