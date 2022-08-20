package com.costular.commonui.components.createtask

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.costular.commonui.R
import com.costular.commonui.theme.AppTheme
import com.costular.commonui.theme.AtomRemindersTheme

@Composable
fun CreateTask(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        shape = MaterialTheme.shapes.small,
        modifier = modifier
            .clip(MaterialTheme.shapes.small)
            .clickable { onClick() },
        elevation = 2.dp,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(AppTheme.dimens.contentMargin),
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
