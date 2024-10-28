package com.costular.designsystem.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import com.costular.designsystem.theme.AppTheme
import com.costular.designsystem.theme.AtomTheme

@Composable
fun OutlinedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = ButtonDefaults.outlinedShape,
    border: BorderStroke? = ButtonDefaults.outlinedButtonBorder(enabled),
    colors: ButtonColors = ButtonDefaults.outlinedButtonColors(),
    elevation: ButtonElevation? = null,
    contentPadding: PaddingValues = PaddingValues(AppTheme.dimens.spacingLarge),
    content: @Composable RowScope.() -> Unit,
) {
    androidx.compose.material3.OutlinedButton(
        onClick,
        modifier = modifier,
        enabled = enabled,
        border = border,
        shape = shape,
        interactionSource = interactionSource,
        elevation = elevation,
        colors = colors,
        contentPadding = contentPadding,
        content = content,
    )
}

@Preview
@Composable
private fun OutlinedButtonPreview() {
    AtomTheme {
        OutlinedButton(onClick = {}) {
            Text("Click me!")
        }
    }
}
