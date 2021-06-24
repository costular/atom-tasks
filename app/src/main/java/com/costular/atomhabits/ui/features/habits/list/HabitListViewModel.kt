package com.costular.atomhabits.ui.features.habits.list

import androidx.lifecycle.viewModelScope
import com.costular.atomhabits.core.net.DispatcherProvider
import com.costular.atomhabits.domain.Async
import com.costular.atomhabits.domain.interactor.GetHabitsInteractor
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
    private val getHabitsInteractor: GetHabitsInteractor
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

}