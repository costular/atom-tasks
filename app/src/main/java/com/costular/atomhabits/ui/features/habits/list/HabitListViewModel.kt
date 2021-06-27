package com.costular.atomhabits.ui.features.habits.list

import androidx.lifecycle.viewModelScope
import com.costular.atomhabits.core.net.DispatcherProvider
import com.costular.atomhabits.domain.Async
import com.costular.atomhabits.domain.InvokeStatus
import com.costular.atomhabits.domain.interactor.AddHabitRecordInteractor
import com.costular.atomhabits.domain.interactor.GetHabitsInteractor
import com.costular.atomhabits.domain.interactor.RemoveHabitRecordInteractor
import com.costular.decorit.presentation.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class HabitListViewModel @Inject constructor(
    private val dispatcher: DispatcherProvider,
    private val getHabitsInteractor: GetHabitsInteractor,
    private val addHabitRecordInteractor: AddHabitRecordInteractor,
    private val removeHabitRecordInteractor: RemoveHabitRecordInteractor
) : MviViewModel<HabitListState>(HabitListState()) {

    init {
        loadHabits()
    }

    fun setSelectedDay(localDate: LocalDate) = viewModelScope.launch {
        setState { copy(selectedDay = localDate) }
        loadHabits()
    }

    fun loadHabits() = viewModelScope.launch {
        val state = withContext(dispatcher.computation) { awaitState() }

        getHabitsInteractor(GetHabitsInteractor.Params(day = state.selectedDay))
        getHabitsInteractor.observe()
            .flowOn(dispatcher.io)
            .onStart { setState { copy(habits = Async.Loading) } }
            .catch { setState { copy(habits = Async.Failure(it)) } }
            .collect { setState { copy(habits = Async.Success(it)) } }
    }

    fun onMarkHabit(habitId: Long, isMarked: Boolean) = viewModelScope.launch {
        val state = withContext(dispatcher.computation) { awaitState() }
        if (isMarked) {
            removeHabitRecordInteractor(RemoveHabitRecordInteractor.Params(habitId, state.selectedDay))
                .flowOn(dispatcher.io)
                .collect { status ->
                    handleMarkStatus(status)
                }
        } else {
            addHabitRecordInteractor(AddHabitRecordInteractor.Params(habitId, state.selectedDay))
                .flowOn(dispatcher.io)
                .collect { status ->
                    handleMarkStatus(status)
                }
        }
    }

    private fun handleMarkStatus(status: InvokeStatus) {
        // TODO: 27/6/21
    }

}