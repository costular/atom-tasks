package com.costular.atomreminders.ui.features.tasks.create

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Today
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.costular.atomreminders.R
import com.costular.atomreminders.ui.common.validation.EmptyError
import com.costular.atomreminders.ui.components.*
import com.costular.atomreminders.ui.theme.AppTheme
import com.costular.atomreminders.ui.theme.AtomRemindersTheme
import com.costular.atomreminders.ui.util.DateTimeFormatters
import com.costular.atomreminders.ui.util.rememberFlowWithLifecycle
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogState
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.time.LocalDate

@ExperimentalComposeUiApi
@Composable
fun CreateTask(
    onNavigateBack: () -> Unit,
    date: LocalDate? = null,
    viewModel: CreateTaskViewModel = hiltViewModel(),
) {
    val dialogState = rememberMaterialDialogState()
    val coroutineScope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current
    val scaffoldState = rememberScaffoldState()
    val state by rememberFlowWithLifecycle(viewModel.state).collectAsState(CreateTaskState.Empty)

    LaunchedEffect(date) {
        if (date != null) {
            viewModel.setDate(date)
        }
    }

    val error = stringResource(R.string.error_generic)

    LaunchedEffect(viewModel) {
        viewModel.uiEvents.collect { event ->
            when (event) {
                is CreateTaskUiEvent.NavigateUp -> onNavigateBack()
                is CreateTaskUiEvent.ShowError -> {
                    coroutineScope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(error)
                    }
                }
            }
        }
    }

    LaunchedEffect(state.selectDate) {
        if (state.selectDate) {
            dialogState.show()
        } else {
            dialogState.hide()
        }
    }

    DatePicker(
        dialogState = dialogState,
        selectedDate = state.date,
        onDatePicked = viewModel::setDate,
        onDismiss = viewModel::closeDateSelection
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState,
    ) {
        CreateTask(
            state,
            actioner = { action ->
                when (action) {
                    is CreateTaskAction.CreateTask -> viewModel.save()
                    is CreateTaskAction.NavigateUp -> onNavigateBack()
                    is CreateTaskAction.SelectDate -> viewModel.selectDate()
                    is CreateTaskAction.UpdateName -> viewModel.setName(action.name)
                    is CreateTaskAction.HideKeyboard -> {
                        keyboardController?.hide()
                    }
                }
            }
        )
    }

}

@Composable
private fun DatePicker(
    dialogState: MaterialDialogState,
    selectedDate: LocalDate,
    onDatePicked: (LocalDate) -> Unit,
    onDismiss: () -> Unit,
) {
    MaterialDialog(
        dialogState = dialogState,
        buttons = {
            positiveButton(stringResource(R.string.ok)) {
                onDismiss()
            }
            negativeButton(stringResource(R.string.cancel)) {
                onDismiss()
            }
        },
        onCloseRequest = { onDismiss() }
    ) {
        datepicker(
            initialDate = selectedDate,
            onDateChange = { date ->
                onDatePicked(date)
            }
        )
    }
}

@Composable
private fun CreateTask(
    state: CreateTaskState,
    actioner: (CreateTaskAction) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(Modifier.fillMaxWidth()) {
            AtomTopBar(
                navigationIcon = {
                    IconButton(onClick = { actioner(CreateTaskAction.NavigateUp) }) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowBack,
                            contentDescription = null
                        )
                    }
                },
                title = {
                    Text(stringResource(R.string.create_task_title))
                },
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(Modifier.height(24.dp))

            ScreenSubheader(
                stringResource(R.string.create_task_what),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = AppTheme.dimens.contentMargin)
                    .padding(vertical = AppTheme.dimens.spacingSmall)
            )

            TaskName(state, actioner)

            Spacer(Modifier.height(24.dp))

            ScreenSubheader(
                stringResource(R.string.create_task_when),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = AppTheme.dimens.contentMargin)
                    .padding(vertical = AppTheme.dimens.spacingSmall)
            )

            DateChip(state, actioner)
        }

        PrimaryButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = AppTheme.dimens.contentMargin)
                .padding(vertical = AppTheme.dimens.spacingLarge),
            onClick = { actioner(CreateTaskAction.CreateTask) },
            enabled = state.canSave,
        ) {
            if (state.isSaving) {
                CircularProgressIndicator()
            } else {
                Text(stringResource(R.string.create_task_save))
            }
        }
    }
}

@Composable
private fun ColumnScope.DateChip(
    state: CreateTaskState,
    actioner: (CreateTaskAction) -> Unit,
) {
    Chip(
        onClick = { actioner(CreateTaskAction.SelectDate) },
        isError = !state.isDateValid,
        modifier = Modifier.padding(horizontal = AppTheme.dimens.contentMargin)
    ) {
        Icon(
            imageVector = Icons.Outlined.Today,
            contentDescription = null
        )
        Spacer(Modifier.width(16.dp))
        Text(
            DateTimeFormatters.dateFormatter.format(state.date),
            style = MaterialTheme.typography.body1
        )
    }

    if (!state.isDateValid) {
        Spacer(Modifier.height(AppTheme.dimens.spacingMedium))
        TextFieldError(stringResource(R.string.create_task_date_in_the_past))
    }
}

@Composable
private fun ColumnScope.TaskName(
    state: CreateTaskState,
    actioner: (CreateTaskAction) -> Unit,
) {
    AtomOutlinedTextField(
        value = state.name.value,
        onValueChange = { actioner(CreateTaskAction.UpdateName(it)) },
        label = { Text(stringResource(R.string.create_task_name)) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = AppTheme.dimens.contentMargin),
        isError = state.name.hasError,
        maxLines = 1,
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = { actioner(CreateTaskAction.HideKeyboard) })
    )

    if (state.name.hasError) {
        val error = when (state.name.errors.first()) {
            EmptyError -> stringResource(R.string.field_error_empty)
            else -> null
        }

        if (error != null) {
            Spacer(Modifier.height(AppTheme.dimens.spacingMedium))
            TextFieldError(error, modifier = Modifier.fillMaxWidth())
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CreateTaskPreview() {
    AtomRemindersTheme {
        CreateTask(
            state = CreateTaskState(

            ),
            actioner = {}
        )
    }
}