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
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.costular.designsystem.components.PrimaryButton
import com.costular.designsystem.theme.AtomTheme

fun Modifier.strikeThrough(
    enabled: Boolean,
    textLayoutResult: TextLayoutResult?,
    color: Color,
    border: Dp = 1.dp,
    progress: () -> Float,
) = this.then(
    Modifier
        .drawWithContent {
            drawContent()

            if (enabled && textLayoutResult != null) {
                val lines = textLayoutResult.lineCount

                val border = border.toPx()

                for (line in 0 until lines) {
                    val lineCenter = textLayoutResult.getLineMiddle(line) + border
                    val progressWidth = textLayoutResult.getLineRight(line) * progress()

                    drawLine(
                        color = color,
                        start = Offset(0f, lineCenter),
                        end = Offset(progressWidth, lineCenter),
                        strokeWidth = border,
                    )
                }
            }
        }
)

private fun TextLayoutResult.getLineMiddle(lineIndex: Int): Float =
    (getLineBottom(lineIndex) / 2) + (getLineTop(lineIndex) / 2)

@Preview
@Composable
fun StrikeThroughPreview(
    @PreviewParameter(StrikeThroughTextPreviewProvider::class) data: StrikeThroughPreviewData
) {
    AtomTheme {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            var textLayoutResult by remember { mutableStateOf<TextLayoutResult?>(null) }

            var isStruckThrough by remember { mutableStateOf(false) }
            val transition = updateTransition(isStruckThrough, label = "Transition")
            val progress by transition.animateFloat(label = "Progress") { state ->
                if (state) 1f else 0f
            }

            Text(
                text = data.text,
                onTextLayout = {
                    textLayoutResult = it
                },
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .strikeThrough(
                        color = Color.Black,
                        textLayoutResult = textLayoutResult,
                        border = 2.dp,
                        progress = { progress },
                        enabled = isStruckThrough,
                    )
            )

            Spacer(Modifier.height(16.dp))

            PrimaryButton(onClick = { isStruckThrough = !isStruckThrough }) {
                Text(text = if (isStruckThrough) "Disable strike through" else "Strike through")
            }
        }
    }
}

data class StrikeThroughPreviewData(
    val text: String,
    val lines: Int,
)

class StrikeThroughTextPreviewProvider : PreviewParameterProvider<StrikeThroughPreviewData> {
    override val values: Sequence<StrikeThroughPreviewData>
        get() = sequenceOf(
            StrikeThroughPreviewData(
                text = "Lorem Ipsum dolor sit",
                lines = 1,
            ),
            StrikeThroughPreviewData(
                text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                lines = 2,
            ),
            StrikeThroughPreviewData(
                text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean fringilla id sem non pharetra",
                lines = 3,
            )
        )

}
