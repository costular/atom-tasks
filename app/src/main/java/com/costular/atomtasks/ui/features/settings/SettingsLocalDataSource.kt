package com.costular.atomtasks.ui.features.settings

import kotlinx.coroutines.flow.Flow

interface SettingsLocalDataSource {
    fun observeTheme(): Flow<String>
    suspend fun setTheme(theme: String)
}
