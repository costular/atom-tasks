package com.costular.atomtasks.data.settings

import com.costular.atomtasks.core.Interactor
import javax.inject.Inject

class SetThemeUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository,
) : Interactor<SetThemeUseCase.Params>() {

    data class Params(
        val theme: Theme,
    )

    override suspend fun doWork(params: Params) {
        settingsRepository.setTheme(params.theme)
    }
}
