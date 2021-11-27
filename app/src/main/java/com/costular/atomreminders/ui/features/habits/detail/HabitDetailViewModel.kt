package com.costular.atomreminders.ui.features.habits.detail

import androidx.lifecycle.viewModelScope
import com.costular.atomreminders.core.net.DispatcherProvider
import com.costular.atomreminders.domain.Async
import com.costular.atomreminders.domain.InvokeError
import com.costular.atomreminders.domain.InvokeStarted
import com.costular.atomreminders.domain.InvokeSuccess
import com.costular.atomreminders.domain.interactor.GetHabitByIdInteractor
import com.costular.atomreminders.domain.interactor.RemoveHabitInteractor
import com.costular.decorit.presentation.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HabitDetailViewModel @Inject constructor(
    private val dispatcher: DispatcherProvider,
    private val getHabitByIdInteractor: GetHabitByIdInteractor,
    private val removeHabitInteractor: RemoveHabitInteractor
) : MviViewModel<HabitDetailState>(HabitDetailState()) {

    fun load(habitId: Long) = viewModelScope.launch {
        getHabitByIdInteractor(GetHabitByIdInteractor.Params(habitId))
        getHabitByIdInteractor.observe()
            .flowOn(dispatcher.io)
            .onStart { setState { copy(habit = Async.Loading) } }
            .catch {
                Timber.e(it)
                setState { copy(habit = Async.Failure(it)) }
            }
            .collect { habit ->
                setState { copy(habit = Async.Success(habit)) }
            }
    }

    fun delete() = viewModelScope.launch {
        val state = withContext(dispatcher.computation) { awaitState() }
        if (state.habit is Async.Success) {
            removeHabitInteractor(RemoveHabitInteractor.Params(state.habit.data.id))
                .flowOn(dispatcher.io)
                .collect { status ->
                    when (status) {
                        is InvokeStarted -> {

                        }
                        is InvokeSuccess -> {
                            sendEvent(HabitDetailEvents.GoBack)
                        }
                        is InvokeError -> {
                            // TODO: 27/6/21
                        }
                    }
                }
        }
    }

}