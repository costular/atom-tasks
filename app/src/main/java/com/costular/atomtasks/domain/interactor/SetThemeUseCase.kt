package com.costular.atomtasks.domain.interactor

import com.costular.atomtasks.domain.Interactor
import com.costular.atomtasks.domain.model.Theme
import com.costular.atomtasks.domain.repository.SettingsRepository

class SetThemeUseCase(
    private val settingsRepository: SettingsRepository,
) : Interactor<SetThemeUseCase.Params>() {

    data class Params(
        val theme: Theme,
    )

    override suspend fun doWork(params: Params) {
        settingsRepository.setTheme(params.theme)
    }
}
