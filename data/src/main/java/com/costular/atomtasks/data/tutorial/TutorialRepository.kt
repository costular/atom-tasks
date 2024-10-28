package com.costular.atomtasks.data.tutorial

import com.costular.atomtasks.core.AtomError
import com.costular.atomtasks.core.Either
import kotlinx.coroutines.flow.Flow

interface TutorialRepository {
    fun shouldShowOnboarding(): Either<AtomError, Flow<Boolean>>
    suspend fun onboardingShown()
    fun shouldShowReorderTaskTutorial(): Flow<Boolean>
    suspend fun reorderTaskShown()
}
