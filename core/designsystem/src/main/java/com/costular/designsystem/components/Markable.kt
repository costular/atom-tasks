package com.costular.designsystem.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Markable(
    isMarked: Boolean,
    onClick: () -> Unit,
    contentColor: Color,
    onContentColor: Color,
    borderColor: Color,
    modifier: Modifier = Modifier,
    borderWidth: Dp = 2.dp,
    width: Dp = 24.dp,
    height: Dp = 24.dp,
    interaction: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    val animatedColor by animateColorAsState(
        if (isMarked) contentColor else Color.Transparent,
        label = "Color"
    )

    Box(
        modifier = modifier
            .width(width)
            .height(height)
            .border(
                width = borderWidth,
                color = borderColor,
                shape = CircleShape,
            )
            .selectable(
                isMarked,
                interactionSource = interaction,
                role = Role.Checkbox,
                indication = rememberRipple(bounded = false),
                enabled = true,
                onClick = onClick,
            )
            .testTag("Markable")
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape)
                .background(animatedColor)
                .padding(4.dp),
            contentAlignment = Alignment.Center,
        ) {
            if (isMarked) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = onContentColor,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MarkableMarkedPreview() {
    Markable(
        isMarked = true,
        borderColor = Color.Black,
        onClick = {},
        contentColor = Color.Cyan,
        onContentColor = Color.Black,
    )
}

@Preview(showBackground = true)
@Composable
private fun MarkableUnmarkedPreview() {
    Markable(
        isMarked = false,
        borderColor = Color.Black,
        onClick = {},
        contentColor = Color.Cyan,
        onContentColor = Color.Black,
    )
}
