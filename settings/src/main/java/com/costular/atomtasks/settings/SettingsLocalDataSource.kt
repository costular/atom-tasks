package com.costular.atomtasks.settings

import kotlinx.coroutines.flow.Flow

interface SettingsLocalDataSource {
    fun observeTheme(): Flow<String>
    suspend fun setTheme(theme: String)
}
