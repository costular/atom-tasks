package com.costular.atomreminders.ui.features.habits.create

import androidx.lifecycle.viewModelScope
import com.costular.atomreminders.core.net.DispatcherProvider
import com.costular.atomreminders.domain.InvokeError
import com.costular.atomreminders.domain.InvokeStarted
import com.costular.atomreminders.domain.InvokeSuccess
import com.costular.atomreminders.domain.interactor.CreateHabitInteractor
import com.costular.atomreminders.domain.model.Repetition
import com.costular.decorit.presentation.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class CreateHabitViewModel @Inject constructor(
    private val dispatcher: DispatcherProvider,
    private val createHabitInteractor: CreateHabitInteractor
) : MviViewModel<CreateHabitState>(CreateHabitState()) {

    fun goToPage(page: Int) {
        sendEvent(CreateHabitEvents.GoToPage(page))
    }

    fun setName(name: String) = viewModelScope.launchSetState {
        copy(name = name)
    }

    fun setRepetition(repetition: Repetition) = viewModelScope.launchSetState {
        copy(repetition = repetition)
    }

    fun save() = viewModelScope.launch {
        val state = withContext(dispatcher.computation) { awaitState() }
        createHabitInteractor(
            CreateHabitInteractor.Params(
                state.name,
                state.repetition,
                state.reminderEnabled,
                state.reminderTime
            )
        )
            .flowOn(dispatcher.io)
            .collect { status ->
                when (status) {
                    is InvokeStarted -> {
                        setState { copy(isSaving = true) }
                    }
                    is InvokeSuccess -> {
                        sendEvent(CreateHabitEvents.SavedSuccessfully)
                    }
                    is InvokeError -> {
                        Timber.e(status.throwable)
                        setState { copy(isSaving = true) }
                    }
                }
            }
    }

    fun setReminderTime(time: LocalTime) = viewModelScope.launchSetState {
        copy(reminderTime = time)
    }

    fun setReminderEnabled(active: Boolean) = viewModelScope.launchSetState {
        copy(reminderEnabled = active)
    }

}