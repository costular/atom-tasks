package com.costular.atomtasks.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun ScreenSubheader(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        style = MaterialTheme.typography.subtitle1,
        modifier = modifier.fillMaxWidth(),
        maxLines = 2,
        overflow = TextOverflow.Ellipsis
    )
}
