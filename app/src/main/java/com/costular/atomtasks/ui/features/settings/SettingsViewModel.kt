package com.costular.atomtasks.ui.features.settings

import androidx.lifecycle.viewModelScope
import com.costular.atomtasks.domain.interactor.GetThemeUseCase
import com.costular.atomtasks.domain.interactor.SetThemeUseCase
import com.costular.atomtasks.domain.model.Theme
import com.costular.atomtasks.ui.mvi.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val getThemeUseCase: GetThemeUseCase,
    private val setThemeUseCase: SetThemeUseCase,
) : MviViewModel<SettingsState>(SettingsState.Empty) {

    init {
        observeTheme()
    }

    fun observeTheme() {
        viewModelScope.launch {
            getThemeUseCase(Unit)
            getThemeUseCase.observe()
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
