package com.costular.atomreminders.ui.components.create_task

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.costular.atomreminders.R
import com.costular.atomreminders.ui.theme.AppTheme
import com.costular.atomreminders.ui.theme.AtomRemindersTheme

@Composable
fun CreateTask(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        shape = MaterialTheme.shapes.small,
        modifier = modifier.clickable { onClick() },
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(AppTheme.dimens.contentMargin)
        ) {
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(
                    text = stringResource(R.string.agenda_create_new_task),
                    style = MaterialTheme.typography.h6,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CreateTaskPreview() {
    AtomRemindersTheme {
        CreateTask(modifier = Modifier.fillMaxWidth(), onClick = {})
    }
}