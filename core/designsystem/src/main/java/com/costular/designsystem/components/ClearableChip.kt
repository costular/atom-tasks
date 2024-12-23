package com.costular.designsystem.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.AssistChipDefaults.assistChipBorder
import androidx.compose.material3.ChipColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.costular.atomtasks.core.ui.R
import com.costular.designsystem.theme.AppTheme
import com.costular.designsystem.theme.AtomTheme
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement as MinimumTouchArea

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClearableChip(
    title: String,
    icon: ImageVector?,
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

    val chipColors = buildChipColors(isSelected = isSelected, isError = isError)

    AssistChip(
        modifier = modifier,
        onClick = onClick,
        leadingIcon = {
            icon?.let {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(AppTheme.ChipIconSize),
                )
            }
        },
        label = {
            Text(
                text = title,
            )
        },
        trailingIcon = {
            if (isSelected) {
                CompositionLocalProvider(MinimumTouchArea provides false) {
                    ClearIcon(onClear = onClear)
                }
            }
        },
        border = border,
        colors = chipColors,
    )
}

@Composable
private fun buildChipColors(
    isSelected: Boolean,
    isError: Boolean
): ChipColors = when {
    isSelected && !isError -> {
        AssistChipDefaults.assistChipColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            labelColor = MaterialTheme.colorScheme.onPrimaryContainer,
            leadingIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            trailingIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        )
    }

    isError -> {
        AssistChipDefaults.assistChipColors(
            containerColor = MaterialTheme.colorScheme.errorContainer,
            labelColor = MaterialTheme.colorScheme.onErrorContainer,
            leadingIconContentColor = MaterialTheme.colorScheme.onErrorContainer,
        )
    }

    else -> AssistChipDefaults.assistChipColors()
}

@Composable
private fun ClearIcon(onClear: () -> Unit) {
    IconButton(
        modifier = Modifier.size(24.dp),
        onClick = onClear,
    ) {
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = stringResource(
                R.string.content_description_chip_clear,
            ),
            modifier = Modifier.size(AppTheme.ChipIconSize),
        )
    }
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
