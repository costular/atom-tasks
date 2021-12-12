package com.costular.atomreminders.ui.features.tasks.create

import androidx.lifecycle.viewModelScope
import com.costular.atomreminders.domain.InvokeError
import com.costular.atomreminders.domain.InvokeStarted
import com.costular.atomreminders.domain.InvokeSuccess
import com.costular.atomreminders.domain.error.AtomError
import com.costular.atomreminders.domain.interactor.CreateTaskInteractor
import com.costular.atomreminders.ui.common.validation.EmptyFieldValidation
import com.costular.atomreminders.ui.common.validation.FieldValidator
import com.costular.atomreminders.ui.mvi.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class CreateTaskViewModel @Inject constructor(
    private val createTaskInteractor: CreateTaskInteractor,
    private val fieldValidator: FieldValidator,
) : MviViewModel<CreateTaskState>(CreateTaskState.Empty) {

    fun setName(name: String) {
        setState {
            copy(name = fieldValidator.validate(name, EmptyFieldValidation))
        }
    }

    fun selectDate() {
        setState {
            copy(selectDate = true)
        }
    }

    fun closeDateSelection() {
        setState {
            copy(selectDate = false)
        }
    }

    fun setDate(localDate: LocalDate) {
        setState { copy(date = localDate) }
    }

    fun setReminder(localTime: LocalTime?) {
        setState { copy(reminder = localTime) }
    }

    fun save() {
        viewModelScope.launch {
            val state = state.value

            createTaskInteractor(CreateTaskInteractor.Params(
                name = state.name.value,
                date = state.date,
                reminderEnabled = state.reminder != null,
                reminderTime = state.reminder)
            ).collect { status ->
                when (status) {
                    is InvokeStarted -> {
                        setState { copy(isSaving = true) }
                    }
                    is InvokeSuccess -> {
                        setState { copy(isSaving = false) }
                        sendEvent(CreateTaskUiEvent.NavigateUp)
                    }
                    is InvokeError -> {
                        // TODO: improve error handling
                        setState { copy(isSaving = false) }
                        sendEvent(CreateTaskUiEvent.ShowError(AtomError.Unknown))
                    }
                }
            }
        }
    }

}