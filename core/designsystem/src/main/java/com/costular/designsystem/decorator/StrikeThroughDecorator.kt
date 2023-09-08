package com.costular.designsystem.decorator

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.costular.designsystem.components.PrimaryButton
import com.costular.designsystem.theme.AtomTheme

fun Modifier.strikeThrough(
    color: Color,
    border: Dp = 1.dp,
    progress: () -> Float,
) = this.then(
    Modifier
        .drawWithContent {
            drawContent()

            val border = border.toPx()
            val halfHeight = size.height / 2f
            val progressWidth = size.width * progress()

            drawLine(
                color = color,
                start = Offset(0f, halfHeight),
                end = Offset(progressWidth, halfHeight),
                strokeWidth = border,
            )
        }
)

@Preview
@Composable
fun StrikeThroughPreview() {
    AtomTheme {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            var isStruckThrough by remember { mutableStateOf(false) }
            val transition = updateTransition(isStruckThrough, label = "Transition")
            val progress by transition.animateFloat(label = "Progress") { state ->
                if (state) 1f else 0f
            }

            Text(
                text = "Lorem Ipsum",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .border(1.dp, Color.Red)
                    .strikeThrough(
                        color = Color.Black,
                        border = 2.dp,
                        progress = { progress },
                    )
            )

            Spacer(Modifier.height(16.dp))

            PrimaryButton(onClick = { isStruckThrough = !isStruckThrough }) {
                Text(text = if (isStruckThrough) "Disable strike through" else "Strike through")
            }
        }
    }
}
