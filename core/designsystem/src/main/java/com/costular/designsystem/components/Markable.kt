package com.costular.designsystem.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.view.HapticFeedbackConstantsCompat
import com.costular.designsystem.theme.AtomTheme

@Composable
fun Markable(
    isMarked: Boolean,
    onMarkChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    borderColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.primary,
    onContentColor: Color = MaterialTheme.colorScheme.onPrimary,
    borderWidth: Dp = 2.dp,
    size: Dp = 24.dp,
    interaction: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    val animatedColor by animateColorAsState(
        if (isMarked) contentColor else Color.Transparent,
        label = "Color",
        animationSpec = tween(
            durationMillis = 400,
            easing = LinearEasing,
        )
    )

    val view = LocalView.current
    val scaleAnimatable = remember { Animatable(1f) }
    var shouldAnimateDone by remember { mutableStateOf(false) }

    LaunchedEffect(shouldAnimateDone) {
        if (shouldAnimateDone) {
            scaleAnimatable.snapTo(0f)

            scaleAnimatable.animateTo(
                targetValue = 1f,
                animationSpec = spring(
                    dampingRatio = 0.4f,
                    stiffness = 400f,
                )
            )
        }
    }

    val scale by scaleAnimatable.asState()

    Box(
        modifier = modifier
            .size(size)
            .scale(scale)
            .minimumInteractiveComponentSize()
            .border(
                width = borderWidth,
                color = borderColor,
                shape = CircleShape,
            )
            .selectable(
                isMarked,
                interactionSource = interaction,
                role = Role.Checkbox,
                indication = ripple(
                    bounded = false,
                    radius = Dp.Unspecified,
                ),
                enabled = true,
                onClick = {
                    if (!isMarked) {
                        shouldAnimateDone = true
                        view.performHapticFeedback(HapticFeedbackConstantsCompat.TOGGLE_ON)
                    } else {
                        shouldAnimateDone = false
                    }

                    onMarkChanged(!isMarked)
                },
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
    AtomTheme {
        Markable(
            isMarked = true,
            onMarkChanged = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MarkableUnmarkedPreview() {
    AtomTheme {
        Markable(
            isMarked = false,
            onMarkChanged = {},
        )
    }
}
