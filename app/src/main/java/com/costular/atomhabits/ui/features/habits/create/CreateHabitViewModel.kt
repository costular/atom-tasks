package com.costular.atomhabits.ui.features.habits.create

import androidx.lifecycle.viewModelScope
import com.costular.atomhabits.core.net.DispatcherProvider
import com.costular.atomhabits.domain.InvokeError
import com.costular.atomhabits.domain.InvokeStarted
import com.costular.atomhabits.domain.InvokeSuccess
import com.costular.atomhabits.domain.interactor.CreateHabitInteractor
import com.costular.atomhabits.domain.model.Repetition
import com.costular.decorit.presentation.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CreateHabitViewModel @Inject constructor(
    private val dispatcher: DispatcherProvider,
    private val createHabitInteractor: CreateHabitInteractor
) : MviViewModel<CreateHabitState>(CreateHabitState()) {

    fun setName(name: String) = viewModelScope.launchSetState {
        copy(name = name)
    }

    fun setRepetition(repetition: Repetition) = viewModelScope.launchSetState {
        copy(repetition = repetition)
    }

    fun save() = viewModelScope.launch {
        val state = withContext(dispatcher.computation) { awaitState() }
        createHabitInteractor(CreateHabitInteractor.Params(state.name, state.repetition))
            .flowOn(dispatcher.io)
            .collect { status ->
                when (status) {
                    is InvokeStarted -> {
                        setState { copy(isSaving = true) }
                    }
                    is InvokeSuccess -> {
                        // TODO: 24/6/21 navigate away
                    }
                    is InvokeError -> {

                    }
                }
            }
    }

}