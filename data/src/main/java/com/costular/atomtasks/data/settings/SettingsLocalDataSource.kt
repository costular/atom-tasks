package com.costular.atomtasks.data.settings

import kotlinx.coroutines.flow.Flow

interface SettingsLocalDataSource {
    fun observeTheme(): Flow<String>
    suspend fun setTheme(theme: String)
}
