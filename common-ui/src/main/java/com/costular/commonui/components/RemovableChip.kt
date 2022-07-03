package com.costular.commonui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentColor
import androidx.compose.material.LocalMinimumTouchTargetEnforcement
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.costular.commonui.theme.AppTheme
import com.costular.commonui.theme.AtomRemindersTheme
import com.costular.common_ui.R

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RemovableChip(
    title: String,
    icon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit,
    onClear: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val backgroundColor = if (isSelected) {
        MaterialTheme.colors.primary
    } else {
        MaterialTheme.colors.surface
    }

    val contentColor = if (isSelected) {
        MaterialTheme.colors.onPrimary
    } else {
        MaterialTheme.colors.onSurface
    }

    Chip(
        modifier = modifier,
        onClick = onClick,
        border = BorderStroke(0.dp, Color.Unspecified),
        contentPadding = PaddingValues(12.dp),
        backgroundColor = backgroundColor,
    ) {
        CompositionLocalProvider(LocalContentColor provides contentColor) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(AppTheme.ChipIconSize),
            )

            Spacer(Modifier.width(AppTheme.dimens.spacingMedium))

            Text(
                text = title,
            )

            if (isSelected) {
                Spacer(Modifier.width(AppTheme.dimens.spacingMedium))

                CompositionLocalProvider(
                    LocalMinimumTouchTargetEnforcement provides false,
                ) {
                    IconButton(
                        onClick = onClear,
                    ) {
                        Image(
                            imageVector = Icons.Default.Close,
                            contentDescription = stringResource(
                                R.string.content_description_chip_clear,
                            ),
                            modifier = Modifier
                                .size(AppTheme.ChipIconSize),
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun RemovableChipPreview() {
    AtomRemindersTheme {
        RemovableChip(
            title = "1 May 2022",
            icon = Icons.Default.CalendarToday,
            isSelected = false,
            onClick = {},
            onClear = {},
        )
    }
}

@Preview
@Composable
fun RemovableChipSelectedPreview() {
    AtomRemindersTheme {
        RemovableChip(
            title = "1 May 2022",
            icon = Icons.Default.CalendarToday,
            isSelected = true,
            onClick = {},
            onClear = {},
        )
    }
}
