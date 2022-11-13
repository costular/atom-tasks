package com.costular.designsystem.components.createtask

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.costular.designsystem.R
import com.costular.designsystem.theme.AtomTheme

@Composable
fun CreateTask(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ExtendedFloatingActionButton(
        modifier = modifier,
        onClick = onClick,
        text = {
            Text(stringResource(R.string.agenda_create_new_task))
        },
        icon = {
            Icon(Icons.Default.Add, contentDescription = null)
        },
    )
}

@Preview(showBackground = true)
@Composable
fun CreateTaskPreview() {
    AtomTheme {
        CreateTask(modifier = Modifier.fillMaxWidth(), onClick = {})
    }
}
