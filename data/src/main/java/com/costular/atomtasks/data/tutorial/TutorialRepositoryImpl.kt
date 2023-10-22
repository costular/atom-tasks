package com.costular.atomtasks.data.tutorial

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TutorialRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) : TutorialRepository {

    private val preferenceShowTaskOrderTutorial = booleanPreferencesKey("tutorial_task_order")
    val showTaskOrderTutorial: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[preferenceShowTaskOrderTutorial] ?: true
    }

    override fun shouldShowReorderTaskTutorial(): Flow<Boolean> = showTaskOrderTutorial

    override suspend fun reorderTaskShown() {
        dataStore.edit { settings ->
            settings[preferenceShowTaskOrderTutorial] = false
        }
    }
}
