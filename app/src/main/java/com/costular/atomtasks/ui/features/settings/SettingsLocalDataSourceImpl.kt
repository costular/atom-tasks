package com.costular.atomtasks.ui.features.settings

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.costular.atomtasks.domain.model.Theme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsLocalDataSourceImpl(
    private val dataStore: DataStore<Preferences>,
) : SettingsLocalDataSource {

    private val THEME = stringPreferencesKey("theme")
    val theme: Flow<String> = dataStore.data
        .map { preferences ->
            preferences[THEME] ?: Theme.SYSTEM
        }

    override fun observeTheme(): Flow<String> = theme

    override suspend fun setTheme(theme: String) {
        dataStore.edit { settings ->
            settings[THEME] = theme
        }
    }
}
