package com.costular.atomhabits.ui.features.habits.create

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.costular.atomhabits.R
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import com.costular.atomhabits.ui.components.ScreenHeader

@Composable
fun CreateHabitName(
    state: CreateHabitState,
    onChangeName: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        ScreenHeader(
            text = stringResource(R.string.create_habit_name_header),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        OutlinedTextField(
            value = state.name,
            onValueChange = { onChangeName(it) },
            label = { Text(stringResource(R.string.create_habit_name_name_label)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 16.dp)
        )
    }
}