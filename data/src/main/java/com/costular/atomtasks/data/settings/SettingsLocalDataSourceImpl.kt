package com.costular.atomtasks.data.settings

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class SettingsLocalDataSourceImpl(
    private val dataStore: DataStore<Preferences>,
) : SettingsLocalDataSource {

    private val preferenceTheme = stringPreferencesKey("theme")
    val theme: Flow<String> = dataStore.data
        .map { preferences ->
            preferences[preferenceTheme] ?: Theme.SYSTEM
        }

    private val preferenceMoveUndoneTasksTomorrow = booleanPreferencesKey("tasks_move_undone")
    val moveUndoneTasksTomorrow: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[preferenceMoveUndoneTasksTomorrow] ?: DefaultMoveUndoneTasks
    }

    override fun observeTheme(): Flow<String> = theme

    override suspend fun setTheme(theme: String) {
        dataStore.edit { settings ->
            settings[preferenceTheme] = theme
        }
    }

    override fun observeMoveUndoneTaskTomorrow(): Flow<Boolean> = moveUndoneTasksTomorrow

    override suspend fun setMoveUndoneTaskTomorrow(isEnabled: Boolean) {
        dataStore.edit { settings ->
            settings[preferenceMoveUndoneTasksTomorrow] = isEnabled
        }
    }

    private companion object {
        const val DefaultMoveUndoneTasks = true
    }
}
