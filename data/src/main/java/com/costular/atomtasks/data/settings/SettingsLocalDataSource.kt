package com.costular.atomtasks.data.settings

import kotlinx.coroutines.flow.Flow

interface SettingsLocalDataSource {
    fun observeTheme(): Flow<String>
    suspend fun setTheme(theme: String)
    fun observeMoveUndoneTaskTomorrow(): Flow<Boolean>
    suspend fun setMoveUndoneTaskTomorrow(isEnabled: Boolean)
}
