package com.costular.atomhabits.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ScreenHeader(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        style = MaterialTheme.typography.h4,
        modifier = modifier
    )
}