package com.costular.atomtasks.data.settings

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    fun observeTheme(): Flow<Theme>
    suspend fun setTheme(theme: Theme)
    fun observeMoveUndoneTaskTomorrow(): Flow<Boolean>
    suspend fun setMoveUndoneTaskTomorrow(isEnabled: Boolean)
}
