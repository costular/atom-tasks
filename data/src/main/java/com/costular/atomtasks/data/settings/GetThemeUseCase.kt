package com.costular.atomtasks.data.settings

import com.costular.atomtasks.core.SubjectInteractor
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetThemeUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository,
) : SubjectInteractor<Unit, Theme>() {

    override fun createObservable(params: Unit): Flow<Theme> =
        settingsRepository.observeTheme()
}
