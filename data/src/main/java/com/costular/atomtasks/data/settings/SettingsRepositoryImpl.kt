package com.costular.atomtasks.data.settings

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class SettingsRepositoryImpl(
    private val settingsLocalDataSource: SettingsLocalDataSource,
) : SettingsRepository {

    override fun observeTheme(): Flow<Theme> =
        settingsLocalDataSource.observeTheme().map { Theme.fromString(it) }

    override suspend fun setTheme(theme: Theme) {
        settingsLocalDataSource.setTheme(theme.asString())
    }

    override fun observeMoveUndoneTaskTomorrow(): Flow<Boolean> =
        settingsLocalDataSource.observeMoveUndoneTaskTomorrow()

    override suspend fun setMoveUndoneTaskTomorrow(isEnabled: Boolean) {
        settingsLocalDataSource.setMoveUndoneTaskTomorrow(isEnabled)
    }
}
