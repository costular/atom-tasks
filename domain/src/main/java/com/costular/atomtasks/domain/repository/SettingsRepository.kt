package com.costular.atomtasks.domain.repository

import com.costular.atomtasks.domain.model.Theme
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    fun observeTheme(): Flow<Theme>
    suspend fun setTheme(theme: Theme)
}
