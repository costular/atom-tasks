package com.costular.atomreminders.ui.components.create_task

import android.widget.DatePicker
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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