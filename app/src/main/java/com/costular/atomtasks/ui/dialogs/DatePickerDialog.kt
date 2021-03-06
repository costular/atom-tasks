package com.costular.atomtasks.ui.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.window.Dialog
import com.costular.atomtasks.R
import com.costular.atomtasks.ui.components.DatePicker
import com.costular.atomtasks.ui.theme.AppTheme
import java.time.LocalDate

@Composable
fun DatePickerDialog(
    onDismiss: () -> Unit,
    currentDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit,
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            color = MaterialTheme.colors.background,
            shape = MaterialTheme.shapes.medium,
        ) {
            Column(modifier = Modifier.padding(AppTheme.dimens.contentMargin)) {
                Text(
                    text = stringResource(R.string.create_task_set_date),
                    style = MaterialTheme.typography.h5,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(top = AppTheme.dimens.spacingMedium)
                        .padding(bottom = AppTheme.dimens.spacingXLarge),
                )

                DatePicker(
                    currentDate = currentDate,
                    onDateSelected = onDateSelected,
                )
            }
        }
    }
}
