package com.costular.atomtasks.domain.interactor

import com.costular.atomtasks.domain.SubjectInteractor
import com.costular.atomtasks.domain.model.Theme
import com.costular.atomtasks.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow

class GetThemeUseCase(
    private val settingsRepository: SettingsRepository,
) : SubjectInteractor<Unit, Theme>() {

    override fun createObservable(params: Unit): Flow<Theme> =
        settingsRepository.observeTheme()
}
