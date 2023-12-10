package com.costular.designsystem.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.AssistChipDefaults.assistChipBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.costular.atomtasks.core.ui.R
import com.costular.designsystem.theme.AppTheme
import com.costular.designsystem.theme.AtomTheme

@Composable
fun ClearableChip(
    title: String,
    icon: ImageVector,
    isSelected: Boolean,
    isError: Boolean,
    onClick: () -> Unit,
    onClear: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val border = if (isError) {
        assistChipBorder(
            enabled = true,
            borderColor = MaterialTheme.colorScheme.error
        )
    } else {
        assistChipBorder(enabled = true)
    }

    val chipColors = if (isError) {
        AssistChipDefaults.assistChipColors(
            containerColor = MaterialTheme.colorScheme.errorContainer,
            labelColor = MaterialTheme.colorScheme.onErrorContainer,
            leadingIconContentColor = MaterialTheme.colorScheme.onErrorContainer,
        )
    } else {
        AssistChipDefaults.assistChipColors()
    }

    AssistChip(
        modifier = modifier,
        onClick = {
            if (isSelected) {
                onClear()
            } else {
                onClick()
            }
        },
        leadingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(AppTheme.ChipIconSize),
            )
        },
        label = {
            Text(
                text = title,
            )
        },
        trailingIcon = {
            if (isSelected) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(
                        R.string.content_description_chip_clear,
                    ),
                    modifier = Modifier
                        .size(AppTheme.ChipIconSize),
                )
            }
        },
        border = border,
        colors = chipColors,
    )
}

@Preview
@Composable
fun ClearableChipPreview() {
    AtomTheme {
        ClearableChip(
            title = "1 May 2022",
            icon = Icons.Default.CalendarToday,
            isSelected = false,
            isError = false,
            onClick = {},
            onClear = {},
        )
    }
}

@Preview
@Composable
fun ClearableChipSelectedPreview() {
    AtomTheme {
        ClearableChip(
            title = "1 May 2022",
            icon = Icons.Default.CalendarToday,
            isSelected = true,
            isError = false,
            onClick = {},
            onClear = {},
        )
    }
}

@Preview
@Composable
fun ClearableChipErrorPreview() {
    AtomTheme {
        ClearableChip(
            title = "1 May 2022",
            icon = Icons.Default.CalendarToday,
            isSelected = false,
            isError = true,
            onClick = {},
            onClear = {},
        )
    }
}

@Preview
@Composable
fun ClearableChipErrorSelectedPreview() {
    AtomTheme {
        ClearableChip(
            title = "1 May 2022",
            icon = Icons.Default.CalendarToday,
            isSelected = true,
            isError = true,
            onClick = {},
            onClear = {},
        )
    }
}
