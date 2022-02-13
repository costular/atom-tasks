package com.costular.atomtasks.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.costular.atomtasks.ui.theme.AppTheme

@Composable
fun TextFieldError(
    textError: String,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        Spacer(modifier = Modifier.width(AppTheme.dimens.contentMargin))
        Text(
            text = textError,
            modifier = Modifier.fillMaxWidth(),
            style = LocalTextStyle.current.copy(color = MaterialTheme.colors.error)
        )
    }
}
