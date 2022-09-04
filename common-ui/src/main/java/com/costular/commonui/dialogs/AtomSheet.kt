package com.costular.commonui.dialogs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.costular.commonui.components.BottomSheetDraggable
import com.costular.commonui.theme.AppTheme
import com.costular.commonui.theme.AtomRemindersTheme

@Composable
fun AtomSheet(
    title: String?,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Column(modifier) {
        Spacer(Modifier.height(AppTheme.dimens.spacingLarge))

        Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            BottomSheetDraggable()
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            val startModifier = Modifier
                .weight(1f)
                .padding(start = AppTheme.dimens.contentMargin)

            if (title != null) {
                AtomSheetTitle(
                    text = title,
                    modifier = startModifier,
                )
            } else {
                Spacer(modifier = startModifier)
            }

            IconButton(onClick = onNavigateUp) {
                Icon(imageVector = Icons.Default.Close, contentDescription = null)
            }
        }

        Box(Modifier.padding(AppTheme.dimens.contentMargin)) {
            content()
        }
    }
}

@Composable
private fun AtomSheetTitle(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        modifier = modifier,
        text = text,
        style = MaterialTheme.typography.titleLarge,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis,
    )
}

@Preview(showBackground = true)
@Composable
fun AtomSheetPreview() {
    AtomRemindersTheme {
        AtomSheet(
            title = "Test",
            content = {
                Text("Lorem Ipsum is simply dummy text of the printing and typesetting industry")
            },
            onNavigateUp = {},
        )
    }
}
