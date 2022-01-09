package com.costular.atomtasks.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.costular.atomtasks.ui.theme.AppTheme

@Composable
fun ExpandableChip(
    isExpanded: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    val angle: Float by animateFloatAsState(
        targetValue = if (isExpanded) 180f else 0F,
        animationSpec = tween(
            durationMillis = 300,
            delayMillis = 200,
            easing = FastOutSlowInEasing
        )
    )

    val backgroundColor = if (isExpanded) {
        MaterialTheme.colors.primary
    } else {
        MaterialTheme.colors.surface
    }

    val contentColor = if (isExpanded) {
        MaterialTheme.colors.onPrimary
    } else {
        MaterialTheme.colors.onSurface
    }

    Chip(
        modifier = modifier,
        onClick = onClick,
        border = BorderStroke(0.dp, Color.Unspecified),
        backgroundColor = backgroundColor,
    ) {
        CompositionLocalProvider(LocalContentColor provides contentColor) {
            content.invoke(this)

            Spacer(Modifier.width(AppTheme.dimens.spacingSmall))

            Icon(
                imageVector = Icons.Default.ExpandMore,
                contentDescription = null,
                modifier = Modifier
                    .size(AppTheme.ChipIconSize)
                    .rotate(angle)
            )
        }
    }

}