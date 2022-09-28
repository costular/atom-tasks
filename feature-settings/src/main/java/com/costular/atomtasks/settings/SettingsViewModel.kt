package com.costular.atomtasks.settings

import androidx.lifecycle.viewModelScope
import com.costular.atomtasks.coreui.mvi.MviViewModel
import com.costular.atomtasks.data.settings.GetMoveUndoneTaskTomorrowUseCase
import com.costular.atomtasks.data.settings.GetThemeUseCase
import com.costular.atomtasks.data.settings.SetMoveUndoneTaskTomorrowUseCase
import com.costular.atomtasks.data.settings.SetThemeUseCase
import com.costular.atomtasks.data.settings.Theme
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val getThemeUseCase: GetThemeUseCase,
    private val setThemeUseCase: SetThemeUseCase,
    private val getMoveUndoneTaskTomorrowUseCase: GetMoveUndoneTaskTomorrowUseCase,
    private val setMoveUndoneTaskTomorrowUseCase: SetMoveUndoneTaskTomorrowUseCase,
) : MviViewModel<SettingsState>(SettingsState.Empty) {

    init {
        observeTheme()
        observeMoveUndoneTaskTomorrow()
    }

    private fun observeMoveUndoneTaskTomorrow() {
        viewModelScope.launch {
            getMoveUndoneTaskTomorrowUseCase.invoke(Unit)
            getMoveUndoneTaskTomorrowUseCase.flow.collect { isEnabled ->
                setState { copy(moveUndoneTasksTomorrowAutomatically = isEnabled) }
            }
        }
    }

    fun setMoveUndoneTaskTomorrow(isEnabled: Boolean) {
        viewModelScope.launch {
            setMoveUndoneTaskTomorrowUseCase(
                SetMoveUndoneTaskTomorrowUseCase.Params(isEnabled),
            ).collect()
        }
    }

    fun observeTheme() {
        viewModelScope.launch {
            getThemeUseCase(Unit)
            getThemeUseCase.flow
                .collect { theme ->
                    setState { copy(theme = theme) }
                }
        }
    }

    fun setTheme(theme: Theme) {
        viewModelScope.launch {
            setThemeUseCase(SetThemeUseCase.Params(theme)).collect()
        }
    }
}
