package com.costular.atomtasks.settings

import com.costular.atomtasks.domain.model.Theme
import com.costular.atomtasks.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsRepositoryImpl(
    private val settingsLocalDataSource: SettingsLocalDataSource,
) : SettingsRepository {

    override fun observeTheme(): Flow<Theme> =
        settingsLocalDataSource.observeTheme()
            .map { Theme.fromString(it) }

    override suspend fun setTheme(theme: Theme) {
        settingsLocalDataSource.setTheme(theme.asString())
    }
}
