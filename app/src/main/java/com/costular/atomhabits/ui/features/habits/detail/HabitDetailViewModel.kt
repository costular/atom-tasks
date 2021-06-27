package com.costular.atomhabits.ui.features.habits.detail

import androidx.lifecycle.viewModelScope
import com.costular.atomhabits.core.net.DispatcherProvider
import com.costular.atomhabits.domain.Async
import com.costular.atomhabits.domain.interactor.GetHabitByIdInteractor
import com.costular.decorit.presentation.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HabitDetailViewModel @Inject constructor(
    private val dispatcher: DispatcherProvider,
    private val getHabitByIdInteractor: GetHabitByIdInteractor
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

}