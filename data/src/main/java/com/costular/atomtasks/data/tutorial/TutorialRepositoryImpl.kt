package com.costular.atomtasks.data.tutorial

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.costular.atomtasks.core.AtomError
import com.costular.atomtasks.core.Either
import com.costular.atomtasks.core.toError
import com.costular.atomtasks.core.toResult
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TutorialRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) : TutorialRepository {

    private val preferenceShowOnboarding = booleanPreferencesKey("tutorial_onboarding")
    val showOnboarding: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[preferenceShowOnboarding] ?: true
    }

    private val preferenceShowTaskOrderTutorial = booleanPreferencesKey("tutorial_task_order")
    val showTaskOrderTutorial: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[preferenceShowTaskOrderTutorial] ?: true
    }

    override fun shouldShowOnboarding(): Either<AtomError, Flow<Boolean>> {
        return try {
            showOnboarding.toResult()
        } catch (e: Exception) {
            AtomError.UnknownError.toError()
        }
    }

    override suspend fun onboardingShown() {
        dataStore.edit { settings ->
            settings[preferenceShowOnboarding] = false
        }
    }

    override fun shouldShowReorderTaskTutorial(): Flow<Boolean> = showTaskOrderTutorial

    override suspend fun reorderTaskShown() {
        dataStore.edit { settings ->
            settings[preferenceShowTaskOrderTutorial] = false
        }
    }
}
