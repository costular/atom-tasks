package com.costular.atomtasks.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
) {
    val modifier = modifier
        .width(width)
        .height(height)
        .border(
            width = borderWidth,
            color = borderColor,
            shape = CircleShape
        )
        .selectable(
            isMarked,
            interactionSource = MutableInteractionSource(),
            role = Role.Checkbox,
            indication = rememberRipple(bounded = false),
            enabled = true,
            onClick = onClick
        )

    if (isMarked) {
        Box(modifier = modifier) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
                    .background(contentColor)
                    .padding(4.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = onContentColor
                )
            }
        }
    } else {
        Box(modifier = modifier)
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
        onContentColor = Color.Black
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
        onContentColor = Color.Black
    )
}