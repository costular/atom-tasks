package com.costular.designsystem.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.window.Dialog
import com.costular.atomtasks.core.ui.R
import com.costular.designsystem.components.DatePicker
import com.costular.designsystem.theme.AppTheme
import java.time.LocalDate

@Composable
fun DatePickerDialog(
    onDismiss: () -> Unit,
    currentDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit,
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = MaterialTheme.shapes.medium,
        ) {
            Column(modifier = Modifier.padding(AppTheme.dimens.contentMargin)) {
                Text(
                    text = stringResource(R.string.create_task_set_date),
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(top = AppTheme.dimens.spacingMedium)
                        .padding(bottom = AppTheme.dimens.spacingXLarge),
                )

                DatePicker(
                    selectedDay = currentDate,
                    onDateSelected = onDateSelected,
                )
            }
        }
    }
}
